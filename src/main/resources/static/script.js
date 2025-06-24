// ========== GLOBALS ==========
let registeredEmail = ""; // Global variable to store registered email
let clientRegisteredEmail = ""; // For client registration OTP

// ========== HOVER DROPDOWN ==========
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

// ========== SLIDESHOW ==========
const images = ["2.jpg", "3.jpg", "4.jpg", "5.jpg", "6.jpg", "7.jpg"];
let current = 0;
const slideshow = document.getElementById("slideshow");
function showSlide(idx) {
  if (slideshow) {
    slideshow.style.backgroundImage = `url(${images[idx]})`;
  }
}
if (slideshow) {
  showSlide(current);
  setInterval(() => {
    current = (current + 1) % images.length;
    showSlide(current);
  }, 3000);
}
window.changeImage = function (step) {
  current = (current + step + images.length) % images.length;
  showSlide(current);
};
// ========== TEST SERVER CONNECTION ==========
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

// ========== MODAL SLIDING ==========
const authModal = document.querySelector('.auth-modal');

// Use querySelectorAll for all possible navigation links and handle accordingly
document.querySelectorAll('.register-link').forEach(link =>
  link.addEventListener("click", e => {
    e.preventDefault();
    authModal.className = 'auth-modal slide-vendor-register';
  })
);
document.querySelectorAll('.login-link').forEach(link =>
  link.addEventListener("click", e => {
    e.preventDefault();
    authModal.className = 'auth-modal slide-vendor-login';
  })
);
document.querySelectorAll('.client-login-link').forEach(link =>
  link.addEventListener("click", e => {
    e.preventDefault();
    authModal.className = 'auth-modal slide-client-login';
  })
);
document.querySelectorAll('.client-register-link').forEach(link =>
  link.addEventListener("click", e => {
    e.preventDefault();
    authModal.className = 'auth-modal slide-client-register';
  })
);
document.querySelectorAll('.vendor-login-link').forEach(link =>
  link.addEventListener("click", e => {
    e.preventDefault();
    authModal.className = 'auth-modal slide-vendor-login';
  })
);

// ========== DOM READY ==========
document.addEventListener("DOMContentLoaded", () => {
  testServerConnection();

  // Hamburger menu
  const menuIcon = document.getElementById("menuIcon");
  const menuDropdown = document.getElementById("menuDropdown");
  menuIcon?.addEventListener("click", (e) => {
    e.stopPropagation();
    menuDropdown.style.display = menuDropdown.style.display === "block" ? "none" : "block";
  });
  document.addEventListener("click", (e) => {
    if (menuIcon && !menuIcon.contains(e.target)) {
      menuDropdown.style.display = "none";
    }
  });

  // --- Vendor LOGIN ---
  if (document.getElementById("loginForm")) {
    document.getElementById("loginForm").addEventListener("submit", async (e) => {
      e.preventDefault();
      const form = e.target;
      const data = {
        email: form.email.value.trim(),
        password: form.password.value,
      };
      if (!data.email || !data.password) {
        alert("❌ Please fill in all fields");
        return;
      }
      try {
        const serverWorking = await testServerConnection();
        if (!serverWorking) {
          alert("❌ Cannot connect to server. Please check if the server is running on port 9090.");
          return;
        }
        const res = await fetch("http://localhost:9090/auth/api-login", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
          },
          body: JSON.stringify(data),
        });
        const responseText = await res.text();
        const contentType = res.headers.get("content-type");
        if (!contentType || !contentType.includes("application/json")) {
          alert("❌ Server returned non-JSON response: " + responseText.substring(0, 100));
          return;
        }
        let result;
        try {
          result = JSON.parse(responseText);
        } catch (parseError) {
          alert("❌ Invalid JSON response from server");
          return;
        }
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
        alert("❌ Login failed: " + err.message);
      }
    });
  }

  // --- Vendor REGISTER ---
  if (document.getElementById("registerForm")) {
    document.getElementById("registerForm").addEventListener("submit", async (e) => {
      e.preventDefault();
      const form = e.target;
      const formData = new FormData(form);
      registeredEmail = formData.get("email");
      if (!registeredEmail || !formData.get("password") || !formData.get("name")) {
        alert("❌ Please fill in all fields");
        return;
      }
      try {
        const response = await fetch("http://localhost:9090/auth/register", {
          method: "POST",
          body: formData,
        });
        const responseText = await response.text();
        const contentType = response.headers.get("content-type");
        if (!contentType || !contentType.includes("application/json")) {
          alert("❌ Server returned non-JSON response. Check server logs.");
          return;
        }
        const result = JSON.parse(responseText);
        if (response.ok) {
          alert("✅ " + result.message);
          document.getElementById("otpModal").style.display = "flex";
        } else {
          alert("❌ " + (result.error || "Registration failed"));
        }
      } catch (error) {
        alert("❌ Registration error: " + error.message);
      }
    });
  }

  // --- Client LOGIN ---
  if (document.getElementById("clientLoginForm")) {
    document.getElementById("clientLoginForm").addEventListener("submit", async (e) => {
      e.preventDefault();
      const form = e.target;
      const data = {
        email: form.email.value.trim(),
        password: form.password.value,
      };
      if (!data.email || !data.password) {
        alert("❌ Please fill in all fields");
        return;
      }
      try {
        const res = await fetch("http://localhost:9090/auth/client-login", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
          },
          body: JSON.stringify(data),
        });
        const responseText = await res.text();
        const contentType = res.headers.get("content-type");
        if (!contentType || !contentType.includes("application/json")) {
          alert("❌ Server returned non-JSON response: " + responseText.substring(0, 100));
          return;
        }
        let result;
        try {
          result = JSON.parse(responseText);
        } catch (parseError) {
          alert("❌ Invalid JSON response from server");
          return;
        }
        if (res.ok && result.id) {
          localStorage.setItem("currentClientId", result.id);
          localStorage.setItem("currentClientEmail", result.email);
          if (result.name) {
            localStorage.setItem("currentClientName", result.name);
          }
          alert("✅ Client login successful!");
          window.location.href = "client-dashboard.html";
        } else {
          throw new Error(result.error || "Login failed");
        }
      } catch (err) {
        alert("❌ Login failed: " + err.message);
      }
    });
  }

  // --- Client REGISTER ---
  if (document.getElementById("clientRegisterForm")) {
    document.getElementById("clientRegisterForm").addEventListener("submit", async (e) => {
      e.preventDefault();
      const form = e.target;
      const formData = new FormData(form);
      clientRegisteredEmail = formData.get("email");
      if (!clientRegisteredEmail || !formData.get("password") || !formData.get("name")) {
        alert("❌ Please fill in all fields");
        return;
      }
      try {
        const response = await fetch("http://localhost:9090/auth/client-register", {
          method: "POST",
          body: formData,
        });
        const responseText = await response.text();
        const contentType = response.headers.get("content-type");
        if (!contentType || !contentType.includes("application/json")) {
          alert("❌ Server returned non-JSON response. Check server logs.");
          return;
        }
        const result = JSON.parse(responseText);
        if (response.ok) {
          alert("✅ " + result.message);
          document.getElementById("otpModal").style.display = "flex";
        } else {
          alert("❌ " + (result.error || "Registration failed"));
        }
      } catch (error) {
        alert("❌ Registration error: " + error.message);
      }
    });
  }

  // --- OTP auto tab ---
  document.querySelectorAll(".otp-box").forEach((box, index, arr) => {
    box.addEventListener("input", () => {
      if (box.value && index < arr.length - 1) arr[index + 1].focus();
    });
  });
});

// ========== OTP SUBMIT ==========
function submitOtp() {
  const otp = Array.from(document.querySelectorAll(".otp-box"))
    .map((i) => i.value)
    .join("");
  // Use registeredEmail for vendor, clientRegisteredEmail for client
  // Prefer the one most recently set (client registration sets clientRegisteredEmail, vendor sets registeredEmail)
  const email = clientRegisteredEmail || registeredEmail;

  if (otp.length === 6 && email) {
    fetch("http://localhost:9090/auth/verify-otp", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
        Accept: "application/json",
      },
      body: new URLSearchParams({ email, otp }),
    })
      .then(async (res) => {
        const responseText = await res.text();
        const contentType = res.headers.get("content-type");
        if (!contentType || !contentType.includes("application/json")) {
          throw new Error("Server returned non-JSON response");
        }
        return JSON.parse(responseText);
      })
      .then((data) => {
        if (data.message) {
          alert("✅ " + data.message);
          document.getElementById("otpModal").style.display = "none";
          window.location.href = "index.html";
        } else {
          throw new Error(data.error || "OTP verification failed");
        }
      })
      .catch((err) => {
        alert("❌ " + err.message);
      });
  } else {
    alert("Please enter a 6-digit OTP.");
  }
}