const API = "http://localhost:8080/api";

const userId = localStorage.getItem("userId");

// 🔥 TOAST FUNCTION
function showToast(msg, color = "bg-green-600") {
    const toast = document.createElement("div");

    toast.innerText = msg;
    toast.className = `fixed bottom-5 right-5 ${color} text-white px-4 py-2 rounded shadow-lg`;

    document.body.appendChild(toast);

    setTimeout(() => toast.remove(), 2500);
}

// 🔥 LOAD HISTORY
window.onload = loadHistory;

function loadHistory() {
    const table = document.getElementById("tableBody");

    table.innerHTML = `
      <tr>
        <td colspan="5" class="text-center p-4 text-gray-400">
          Loading appointments...
        </td>
      </tr>
    `;

    fetch(`${API}/appointments/user/${userId}`)
        .then(res => res.json())
        .then(data => {
            table.innerHTML = "";

            if (data.length === 0) {
                table.innerHTML = `
                    <tr>
                        <td colspan="5" class="p-6 text-center text-gray-400 text-lg">
                            No appointments yet 😔
                        </td>
                    </tr>
                `;
                return;
            }

            data.forEach(a => {
                table.innerHTML += `
                    <tr class="border-b border-gray-800 hover:bg-zinc-800 transition">

                        <td class="p-2 font-semibold">${a.provider.name}</td>

                        <td class="p-2">${a.slot.date}</td>

                        <td class="p-2">
                            ${a.slot.startTime} - ${a.slot.endTime}
                        </td>

                        <td class="p-2 font-semibold
                            ${a.status === 'BOOKED' ? 'text-green-400' : ''}
                            ${a.status === 'CANCELLED' ? 'text-red-400' : ''}
                        ">
                            ${a.status}
                        </td>

                        <td class="p-2">
                            ${
                    a.status === 'BOOKED'
                        ? `
        <button onclick="cancel(${a.id})"
            class="bg-red-600 px-3 py-1 rounded hover:bg-red-700 transition mr-2">
            Cancel
        </button>

        <button onclick="reschedule(${a.id}, ${a.provider.id})"
            class="bg-yellow-500 px-3 py-1 rounded hover:bg-yellow-600 transition">
            Reschedule
        </button>
      `
                        : `<span class="text-gray-500">—</span>`
                }
                        </td>

                    </tr>
                `;
            });
        })
        .catch(err => {
            console.error(err);
            showToast("Failed to load data ❌", "bg-red-600");
        });
}

// 🔥 CANCEL
function cancel(id) {
    fetch(`${API}/appointments/cancel/${id}`, {
        method: "PUT"
    })
        .then(res => res.json())
        .then(data => {
            showToast("Appointment Cancelled ❌", "bg-red-600");
            loadHistory();
        })
        .catch(err => {
            console.error(err);
            showToast("Cancel failed ❌", "bg-red-600");
        });
}

// 🔥 RESCHEDULE SYSTEM

let currentAppointmentId = null;
let currentProviderId = null;
let selectedSlotId = null;

function reschedule(id, providerId){
    currentAppointmentId = id;
    currentProviderId = providerId;
    document.getElementById("modal").classList.remove("hidden");
}

function closeModal(){
    document.getElementById("modal").classList.add("hidden");
}

function loadSlotsForReschedule(){

    let date = document.getElementById("rescheduleDate").value;

    if(!date){
        showToast("Select date ⚠️", "bg-yellow-600");
        return;
    }

    fetch(`${API}/slots/available/${currentProviderId}/${date}`)
        .then(res => res.json())
        .then(data => {

            const container = document.getElementById("slotContainer");
            container.innerHTML = "";

            data.forEach(s => {
                container.innerHTML += `
                    <div onclick="selectSlot(${s.id}, this)"
                         class="p-2 bg-zinc-800 rounded text-center cursor-pointer hover:bg-yellow-500">
                        ${s.startTime} - ${s.endTime}
                    </div>
                `;
            });
        });
}

function selectSlot(id, el){
    selectedSlotId = id;

    document.querySelectorAll("#slotContainer div")
        .forEach(d => d.classList.remove("bg-yellow-500"));

    el.classList.add("bg-yellow-500");
}

function confirmReschedule(){

    if(!selectedSlotId){
        showToast("Select slot ⚠️", "bg-yellow-600");
        return;
    }

    fetch(`${API}/appointments/reschedule/${currentAppointmentId}/${selectedSlotId}`, {
        method: "PUT"
    })
        .then(res => res.json())
        .then(data => {
            showToast("Rescheduled Successfully 🔄");
            closeModal();
            loadHistory();
        })
        .catch(err => {
            console.error(err);
            showToast("Reschedule failed ❌", "bg-red-600");
        });
}