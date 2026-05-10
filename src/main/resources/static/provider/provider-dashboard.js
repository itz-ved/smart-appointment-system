const API = "http://localhost:8080/api";

const providerId = localStorage.getItem("providerId");

// 🔥 LOAD SLOTS ON PAGE LOAD
window.onload = loadSlots;

// 🔥 ✅ TIME FORMAT
function formatTime(time){
    let [hour, minute] = time.split(":");

    hour = parseInt(hour);

    let ampm = hour >= 12 ? "PM" : "AM";
    hour = hour % 12 || 12;

    return `${hour}:${minute} ${ampm}`;
}

// 🔥 CREATE SLOT
function createSlot() {
    const date = document.getElementById("date").value;
    const startTime = document.getElementById("startTime").value;
    const endTime = document.getElementById("endTime").value;

    if (!date || !startTime || !endTime) {
        alert("Please fill all fields");
        return;
    }

    fetch(`${API}/slots/add/${providerId}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            date: date,
            startTime: startTime,
            endTime: endTime
        })
    })
        .then(async res => {
            if (!res.ok) {
                let errorMsg = "Something went wrong ❌";

                try {
                    const text = await res.text();

                    if (text.includes("message")) {
                        const json = JSON.parse(text);
                        errorMsg = json.message || text;
                    } else {
                        errorMsg = text;
                    }

                } catch {
                    errorMsg = "Server error ❌";
                }

                throw new Error(errorMsg);
            }

            return res.json();
        })
        .then(data => {
            document.getElementById("message").innerText = "✅ Slot Added Successfully!";
            loadSlots();
        })
        .catch(err => {
            document.getElementById("message").innerText = "❌ " + err.message;
        });
}

// 🔥 LOAD PROVIDER SLOTS
function loadSlots() {
    fetch(`${API}/slots/provider/${providerId}`)
        .then(res => res.json())
        .then(data => {
            const table = document.getElementById("slotTable");
            table.innerHTML = "";

            if (data.length === 0) {
                table.innerHTML = `<tr><td class="p-2">No slots found</td></tr>`;
                return;
            }

            data.forEach(slot => {

                let now = new Date();
                let today = now.toLocaleDateString('en-CA');

                let displayStatus = slot.status;

                // 🔥 TODAY + TIME CHECK
                if (slot.date === today && slot.status === "AVAILABLE") {

                    let [hour, minute] = slot.startTime.split(":");
                    let slotTime = new Date();

                    slotTime.setHours(parseInt(hour));
                    slotTime.setMinutes(parseInt(minute));
                    slotTime.setSeconds(0);

                    if (now > slotTime) {
                        displayStatus = "EXPIRED";
                    }

                }
                // 🔥 PAST DATE
                else if (slot.date < today && slot.status === "AVAILABLE") {
                    displayStatus = "EXPIRED";
                }

                table.innerHTML += `
                    <tr class="border-b border-gray-800 hover:bg-zinc-800 transition">

                        <td class="p-2">${slot.date}</td>

                        <td class="p-2">
                            ${formatTime(slot.startTime)} - ${formatTime(slot.endTime)}
                        </td>

                        <td class="p-2 font-semibold
                            ${displayStatus === 'AVAILABLE' ? 'text-green-400' : ''}
                            ${displayStatus === 'BOOKED' ? 'text-red-400' : ''}
                            ${displayStatus === 'EXPIRED' ? 'text-gray-400' : ''}
                        ">
                            ${displayStatus}
                        </td>

                    </tr>
                `;
            });
        });
}

// 🔥 GO TO BOOKINGS PAGE
function goBookings() {
    window.location.href = "/provider/provider-bookings.html";
}