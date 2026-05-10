const API = "http://localhost:8080/api";

const providerId = localStorage.getItem("providerId");

// 🔐 Protect
if(!providerId){
    window.location.href = "/provider/provider-login.html";
}

window.onload = loadBookings;

// ✅ Load bookings
async function loadBookings(){

    const res = await fetch(`${API}/appointments/provider/${providerId}`);
    const data = await res.json();

    const table = document.getElementById("tableBody");
    table.innerHTML = "";

    if(data.length === 0){
        table.innerHTML = `
            <tr>
                <td colspan="4" class="p-4 text-center text-gray-400">
                    No bookings found 😔
                </td>
            </tr>
        `;
        return;
    }

    data.forEach(a => {

        const row = `
        <tr class="border-b border-gray-800 hover:bg-zinc-800 transition text-sm">

            <td class="p-3 font-semibold text-white">
                ${a.user.name}
            </td>

            <td class="p-3 text-gray-300">
                ${a.slot.date}
            </td>

            <td class="p-3 text-gray-400">
                ${a.slot.startTime} - ${a.slot.endTime}
            </td>

            <td class="p-3">
                <span class="px-3 py-1 rounded-full text-xs font-bold
                    ${a.status === 'BOOKED' ? 'bg-green-600 text-white' : ''}
                    ${a.status === 'CANCELLED' ? 'bg-red-600 text-white' : ''}
                ">
                    ${a.status}
                </span>
            </td>

        </tr>
        `;

        table.innerHTML += row;
    });
}