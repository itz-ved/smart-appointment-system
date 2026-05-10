document.getElementById("providerForm").addEventListener("submit", async function(e) {
    e.preventDefault();

    const provider = {
        name: document.getElementById("name").value,
        serviceType: document.getElementById("serviceType").value,
        experience: document.getElementById("experience").value,
        location: document.getElementById("location").value,
        fees: parseFloat(document.getElementById("fees").value),
        email: document.getElementById("email").value,
        phone: document.getElementById("phone").value
    };

    try {
        const response = await fetch("http://localhost:8080/api/providers/add", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(provider)
        });

        const data = await response.text();

        document.getElementById("message").innerText = data;
        document.getElementById("message").style.color = "green";

    } catch (error) {
        document.getElementById("message").innerText = "Server Error";
    }
});