let registeredEmail = ""; // Global variable to store registered email

// Hover dropdown
document.querySelectorAll(".nav-item").forEach((item) => {
  item.addEventListener("mouseenter", () => {
    const dropdown = item.querySelector(".dropdown");
    if (dropdown) dropdown.style.display = "block";
  });

  item.addEventListener("mouseleave", () => {
    const dropdown = item.querySelector(".dropdown");
    if (dropdown) dropdown.style.display = "none";
  });
});

// Slideshow
const slideshow = document.getElementById("slideshow");
const images = ["2.jpg", "3.jpg", "4.jpg", "5.jpg", "6.jpg", "7.jpg"];
let current = 0;
slideshow.style.backgroundImage = `url(${images[current]})`;
setInterval(() => {
  current = (current + 1) % images.length;
  slideshow.style.backgroundImage = `url(${images[current]})`;
}, 3000);

window.changeImage = (step) => {
  current = (current + step + images.length) % images.length;
  slideshow.style.backgroundImage = `url(${images[current]})`;
};

// Test server connection
async function testServerConnection() {
  try {
    const response = await fetch("http://localhost:9090/auth/test");
    const data = await response.json();
    console.log("Server test successful:", data);
    return true;
  } catch (error) {
    console.error("Server test failed:", error);
    return false;
  }
}

document.addEventListener("DOMContentLoaded", () => {
  // Test server connection on page load
  testServerConnection();

  // Hamburger menu
  const menuIcon = document.getElementById("menuIcon");
  const menuDropdown = document.getElementById("menuDropdown");

  menuIcon?.addEventListener("click", (e) => {
    e.stopPropagation();
    menuDropdown.style.display = menuDropdown.style.display === "block" ? "none" : "block";
  });

  document.addEventListener("click", (e) => {
    if (!menuIcon.contains(e.target)) {
      menuDropdown.style.display = "none";
    }
  });

  // Auth modal
  const loginBox = document.querySelector(".form-box.login");
  const registerBox = document.querySelector(".form-box.register");
  const authModal = document.querySelector(".auth-modal");

  document.querySelector(".register-link").addEventListener("click", (e) => {
    e.preventDefault();
    authModal.classList.add("slide");
    loginBox.style.transform = "translateX(-400px)";
    registerBox.style.transform = "translateX(0)";
  });

  document.querySelector(".login-link").addEventListener("click", (e) => {
    e.preventDefault();
    authModal.classList.remove("slide");
    loginBox.style.transform = "translateX(0)";
    registerBox.style.transform = "translateX(400px)";
  });

  // LOGIN
  document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const form = e.target;
    const data = {
      email: form.email.value.trim(),
      password: form.password.value,
    };

    // Basic validation
    if (!data.email || !data.password) {
      alert("❌ Please fill in all fields");
      return;
    }

    try {
      console.log("=== FRONTEND LOGIN ATTEMPT ===");
      console.log("Email:", data.email);
      console.log("Password:", data.password ? "[PRESENT]" : "[MISSING]");

      // First test server connection
      const serverWorking = await testServerConnection();
      if (!serverWorking) {
        alert("❌ Cannot connect to server. Please check if the server is running on port 9090.");
        return;
      }

      console.log("Making login request...");
      // Changed endpoint to /api-login to avoid conflict
      const res = await fetch("http://localhost:9090/auth/api-login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
        },
        body: JSON.stringify(data),
      });

      console.log("Response received:");
      console.log("Status:", res.status);
      console.log("Status Text:", res.statusText);
      console.log("Headers:", Object.fromEntries(res.headers.entries()));

      // Get response text first to see what we're actually receiving
      const responseText = await res.text();
      console.log("Raw response:", responseText);

      // Check if response is JSON
      const contentType = res.headers.get("content-type");
      console.log("Content-Type:", contentType);

      if (!contentType || !contentType.includes("application/json")) {
        console.error("Non-JSON response received:");
        console.error("Content-Type:", contentType);
        console.error("Response body:", responseText);

        // Show more specific error message
        if (responseText.includes("<!DOCTYPE")) {
          alert(
            "❌ Server returned HTML page instead of JSON. This usually means:\n1. Wrong URL/endpoint\n2. Server error\n3. Security configuration issue\n\nCheck server console logs."
          );
        } else {
          alert("❌ Server returned non-JSON response: " + responseText.substring(0, 100));
        }
        return;
      }

      // Parse JSON response
      let result;
      try {
        result = JSON.parse(responseText);
      } catch (parseError) {
        console.error("JSON parse error:", parseError);
        alert("❌ Invalid JSON response from server");
        return;
      }

      console.log("Parsed response:", result);

      if (res.ok && result.id) {
        localStorage.setItem("currentVendorId", result.id);
        localStorage.setItem("currentVendorEmail", result.email);
        if (result.name) {
          localStorage.setItem("currentVendorName", result.name);
        }
        alert("✅ Login successful!");
        window.location.href = "details.html";
      } else {
        throw new Error(result.error || "Login failed");
      }
    } catch (err) {
      console.error("Login error:", err);
      alert("❌ Login failed: " + err.message);
    }
  });

  // REGISTER
  document.getElementById("registerForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const form = e.target;
    const formData = new FormData(form);
    registeredEmail = formData.get("email"); // Save for OTP

    // Basic validation
    if (!registeredEmail || !formData.get("password") || !formData.get("name")) {
      alert("❌ Please fill in all fields");
      return;
    }

    try {
      console.log("=== FRONTEND REGISTRATION ATTEMPT ===");
      console.log("Email:", registeredEmail);

      const response = await fetch("http://localhost:9090/auth/register", {
        method: "POST",
        body: formData,
      });

      console.log("Registration response status:", response.status);

      const responseText = await response.text();
      console.log("Registration raw response:", responseText);

      const contentType = response.headers.get("content-type");
      if (!contentType || !contentType.includes("application/json")) {
        console.error("Non-JSON response:", responseText);
        alert("❌ Server returned non-JSON response. Check server logs.");
        return;
      }

      const result = JSON.parse(responseText);
      console.log("Registration parsed response:", result);

      if (response.ok) {
        alert("✅ " + result.message);
        document.getElementById("otpModal").style.display = "flex";
      } else {
        alert("❌ " + (result.error || "Registration failed"));
      }
    } catch (error) {
      console.error("Registration error:", error);
      alert("❌ Registration error: " + error.message);
    }
  });

  // OTP auto tab
  document.querySelectorAll(".otp-box").forEach((box, index, arr) => {
    box.addEventListener("input", () => {
      if (box.value && index < arr.length - 1) arr[index + 1].focus();
    });
  });
});

// OTP submit
function submitOtp() {
  const otp = Array.from(document.querySelectorAll(".otp-box"))
    .map((i) => i.value)
    .join("");

  if (otp.length === 6 && registeredEmail) {
    console.log("=== FRONTEND OTP SUBMISSION ===");
    console.log("Email:", registeredEmail);
    console.log("OTP:", otp);

    fetch("http://localhost:9090/auth/verify-otp", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
        Accept: "application/json",
      },
      body: new URLSearchParams({ email: registeredEmail, otp }),
    })
      .then(async (res) => {
        console.log("OTP response status:", res.status);

        const responseText = await res.text();
        console.log("OTP raw response:", responseText);

        const contentType = res.headers.get("content-type");
        if (!contentType || !contentType.includes("application/json")) {
          console.error("Non-JSON response:", responseText);
          throw new Error("Server returned non-JSON response");
        }

        return JSON.parse(responseText);
      })
      .then((data) => {
        console.log("OTP parsed response:", data);

        if (data.message) {
          alert("✅ " + data.message);
          document.getElementById("otpModal").style.display = "none";
          window.location.href = "index.html";
        } else {
          throw new Error(data.error || "OTP verification failed");
        }
      })
      .catch((err) => {
        console.error("OTP error:", err);
        alert("❌ " + err.message);
      });
  } else {
    alert("Please enter a 6-digit OTP.");
  }
}