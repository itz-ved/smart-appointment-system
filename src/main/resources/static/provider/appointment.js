// 🔥 TOAST FUNCTION
function showToast(msg, color = "bg-green-600") {
    const toast = document.createElement("div");

    toast.innerText = msg;
    toast.className = `fixed bottom-5 right-5 ${color} text-white px-4 py-2 rounded shadow-lg`;

    document.body.appendChild(toast);

    setTimeout(() => toast.remove(), 2500);
}

const API = "http://localhost:8080/api";

let selectedSlot = null;
let providerId = localStorage.getItem("providerId");
let userId = localStorage.getItem("userId");

// 🔥 USER NAME
const userName = localStorage.getItem("name");
if (userName && document.getElementById("username")) {
    document.getElementById("username").innerText = "Hi, " + userName;
}

// 🔥 ✅ ADD THIS FUNCTION (IMPORTANT)
function formatTime(time){
    let [hour, minute] = time.split(":");

    hour = parseInt(hour);

    let ampm = hour >= 12 ? "PM" : "AM";
    hour = hour % 12 || 12;

    return `${hour}:${minute} ${ampm}`;
}

// 🔥 LOAD SLOTS
function loadSlots() {
    const date = document.getElementById("date").value;

    if (!date) {
        showToast("Select date first ❗", "bg-red-600");
        return;
    }

    const container = document.getElementById("slots");
    container.innerHTML = "<p class='text-gray-400'>Loading slots...</p>";

    fetch(`${API}/slots/available/${providerId}/${date}`)
        .then(res => res.json())
        .then(data => {
            container.innerHTML = "";

            if (data.length === 0) {
                container.innerHTML = `
                  <div class="text-center col-span-full">
                    <p class="text-gray-400 text-lg">No slots available 😔</p>
                  </div>
                `;
                return;
            }

            data.forEach(slot => {
                const div = document.createElement("div");

                div.className = "slot bg-zinc-900 p-4 rounded text-center cursor-pointer hover:scale-105 hover:shadow-red-500/40 hover:shadow-lg transition";

                // 🔥 ONLY CHANGE HERE
                div.innerHTML = `
                    <p class="font-bold">
                        ${formatTime(slot.startTime)} - ${formatTime(slot.endTime)}
                    </p>
                `;

                div.onclick = () => selectSlot(div, slot.id);

                container.appendChild(div);
            });
        });
}

// 🔥 SELECT SLOT
function selectSlot(element, slotId) {
    document.querySelectorAll(".slot").forEach(s => s.classList.remove("selected"));

    element.classList.add("selected");
    selectedSlot = slotId;
}

// 🔥 BOOK
function book() {
    if (!selectedSlot) {
        showToast("Please select a slot ❗", "bg-red-600");
        return;
    }

    fetch(API + "/appointments/book", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            userId: userId,
            providerId: providerId,
            slotId: selectedSlot
        })
    })
        .then(res => res.json())
        .then(data => {
            showToast("Booking Successful 🎉");

            setTimeout(() => {
                window.location.href = "/success.html";
            }, 1200);
        })
        .catch(err => {
            console.error(err);
            showToast("Booking failed ❌", "bg-red-600");
        });
}

// 🔥 LOGOUT
function logout() {
    localStorage.clear();
    window.location.href = "/user/login.html";
}