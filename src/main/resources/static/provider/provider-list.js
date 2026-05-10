const API = "http://localhost:8080/api";

let allProviders = [];

// 🔥 LOAD USER NAME
document.addEventListener("DOMContentLoaded", () => {

    const userName = localStorage.getItem("name");
    const usernameEl = document.getElementById("username");

    if (userName && usernameEl) {
        usernameEl.innerText = "Hi, " + userName;
    }

    loadProviders(); // ✅ yaha call karo

});

// 🔥 LOAD PROVIDERS
function loadProviders() {
    const loading = document.getElementById("loading");
    loading.classList.remove("hidden");

    fetch(API + "/providers/all")
        .then(res => res.json())
        .then(data => {
            allProviders = data;
            renderProviders();
            loading.classList.add("hidden"); // hide after load
        });
}

// 🔥 RENDER PROVIDERS (NETFLIX STYLE)
function renderProviders() {
    const container = document.getElementById("providerContainer");
    container.innerHTML = "";

    const search = document.getElementById("search").value.toLowerCase();
    const filter = document.getElementById("filter").value;

    const filtered = allProviders.filter(p => {
        return (
            (!filter || p.serviceType.toLowerCase() === filter.toLowerCase()) &&
            (
                p.name.toLowerCase().includes(search) ||
                (p.location && p.location.toLowerCase().includes(search))
            )
        );
    });

    if (filtered.length === 0) {
        container.innerHTML = `<p class="text-gray-400">No providers found</p>`;
        return;
    }

    filtered.forEach(p => {
        container.innerHTML += `
            <div class="bg-zinc-900 p-5 rounded-xl shadow-lg hover:scale-105 transition duration-300 cursor-pointer">

                <h3 class="text-xl font-bold">${p.name}</h3>

                <p class="text-gray-400 mt-1 capitalize">
                    ${p.serviceType}
                </p>

                <p class="mt-2 text-sm text-gray-500">
                    📍 ${p.location || "Not specified"}
                </p>

                <p class="mt-2 text-sm text-gray-500">
                    💼 ${p.experience || "N/A"}
                </p>

                <p class="mt-2 text-red-500 font-bold text-lg">
                    ₹ ${p.fees}
                </p>

                <button
                    onclick="bookProvider(${p.id})"
                    class="mt-4 w-full bg-red-600 py-2 rounded hover:bg-red-700 transition">
                    Book Now
                </button>

            </div>
        `;
    });
}

// 🔥 SEARCH LIVE
document.getElementById("search").addEventListener("input", renderProviders);

// 🔥 BOOK
function bookProvider(id) {
    localStorage.setItem("providerId", id);
    window.location.href = "/provider/appointment.html";
}

// 🔥 LOGOUT
function logout() {
    localStorage.clear();
    window.location.href = "/user/login.html";
}

// 🔥 INIT
loadProviders();