const API = "http://localhost:8080/api";

async function loginProvider() {

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const res = await fetch(`${API}/providers/login`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({ email, password })
    });

    const data = await res.json();

    document.getElementById("message").innerText = data.message;

    if(data.providerId){
        localStorage.setItem("providerId", data.providerId);
        localStorage.setItem("providerName", data.name);

        window.location.href = "/provider/provider-dashboard.html";
    }

}
async function registerProvider() {

    const provider = {
        name: document.getElementById("name").value,
        serviceType: document.getElementById("serviceType").value,
        experience: document.getElementById("experience").value,
        location: document.getElementById("location").value,
        fees: document.getElementById("fees").value,
        email: document.getElementById("email").value,
        phone: document.getElementById("phone").value,
        password: document.getElementById("password").value
    };

    if(!provider.name || !provider.email || !provider.password){
        alert("Fill required fields");
        return;
    }

    const res = await fetch(`${API}/providers/add`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(provider)
    });

    const data = await res.text();

    document.getElementById("message").innerText = data;

    if(res.ok){
        setTimeout(() => {
            window.location.href = "/provider/provider-login.html";
        }, 1000);
    }
}