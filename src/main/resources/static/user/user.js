const API = "http://localhost:8080/api";

// 🔹 Navigate
function goLogin(){
    window.location.href = "/user/login.html";
}

function goRegister(){
    window.location.href = "/user/user-register.html";
}

// 🔥 TOAST FUNCTION
function showToast(msg, color = "bg-green-600") {
    const toast = document.createElement("div");

    toast.innerText = msg;
    toast.className = `fixed bottom-5 right-5 ${color} text-white px-4 py-2 rounded shadow-lg`;

    document.body.appendChild(toast);

    setTimeout(() => {
        toast.remove();
    }, 2500);
}

// ✅ REGISTER
async function register() {
    const btn = document.getElementById("registerBtn");

    const user = {
        name: document.getElementById("name")?.value,
        email: document.getElementById("email")?.value,
        password: document.getElementById("password")?.value,
        phone: document.getElementById("phone")?.value
    };

    // 🔥 Validation
    if(!user.name || !user.email || !user.password){
        showToast("Please fill all fields ❗", "bg-red-600");
        return;
    }

    // 🔥 Button loading
    if(btn){
        btn.innerText = "Registering...";
        btn.disabled = true;
    }


    try {
        const res = await fetch(`${API}/users/register`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(user)
        });

        const data = await res.json();

        if(data.userId){
            localStorage.setItem("userId", data.userId);
            localStorage.setItem("name", data.name);

            showToast(data.message || "Registered Successfully 🎉");

            setTimeout(() => {
                window.location.href = "/provider/provider-list.html";
            }, 1200);
        } else {
            showToast(data.message || "Registration failed ❌", "bg-red-600");
        }

    } catch (err) {
        console.error(err);
        showToast("Error occurred ❌", "bg-red-600");
    }

    if(btn){
        btn.innerText = "Register";
        btn.disabled = false;
    }
}

// ✅ LOGIN
async function login() {
    const btn = document.getElementById("loginBtn");

    const email = document.getElementById("email")?.value;
    const password = document.getElementById("password")?.value;

    if(!email || !password){
        showToast("Please fill all fields ❗", "bg-red-600");
        return;
    }

    // 🔥 Button loading
    if(btn){
        btn.innerText = "Logging in...";
        btn.disabled = true;
    }

    try {
        const res = await fetch(`${API}/users/login`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({ email, password })
        });

        const data = await res.json();

        if(data.userId){
            localStorage.setItem("userId", data.userId);
            localStorage.setItem("name", data.name);

            showToast(data.message || "Login Successful 🎉");

            setTimeout(() => {
                window.location.href = "/provider/provider-list.html";
            }, 1200);
        } else {
            showToast(data.message || "Invalid credentials ❌", "bg-red-600");
        }

    } catch (err) {
        console.error(err);
        showToast("Login failed ❌", "bg-red-600");
    }

    if(btn){
        btn.innerText = "Login";
        btn.disabled = false;
    }
}