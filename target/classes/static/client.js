// Global variable to store companyId after registration
let currentCompanyId = null

// --- Section Navigation Logic (ONLY DECLARATION) ---
function showSection(sectionId, navId) {
  console.log(`Switching to section: ${sectionId}`)
  document.querySelectorAll(".section").forEach((sec) => sec.classList.remove("active-section"))
  const targetSection = document.getElementById(sectionId)
  if (targetSection) {
    targetSection.classList.add("active-section")
  }
  document.querySelectorAll(".navbar a").forEach((a) => a.classList.remove("active"))
  if (navId) {
    const navElement = document.getElementById(navId)
    if (navElement) {
      navElement.classList.add("active")
    }
  }
}

// --- Wait for DOM to be ready ---
document.addEventListener("DOMContentLoaded", () => {
  console.log("DOM loaded, initializing application...")

  // Initialize all functionality
  initializeNavigation()
  initializeFormHandlers()
  initializeMSMEHandlers()
  initializeDocumentUpload()
  initializeProjectHandlers()
  initializeOngoingProjects()
  initializeSupportModal()
  renderIrnTable()

  console.log("Application initialized successfully")
})

// --- Navigation Initialization ---
function initializeNavigation() {
  // Navigation click handlers
  const navClientInfo = document.getElementById("nav-client-info")
  if (navClientInfo) {
    navClientInfo.onclick = (e) => {
      e.preventDefault()
      showSection("section-client-info", "nav-client-info")
    }
  }

  const navCompanyInfo = document.getElementById("nav-company-info")
  if (navCompanyInfo) {
    navCompanyInfo.onclick = (e) => {
      e.preventDefault()
      showSection("section-company-info", "nav-company-info")
    }
  }

  const navDocuments = document.getElementById("nav-documents")
  if (navDocuments) {
    navDocuments.onclick = (e) => {
      e.preventDefault()
      showSection("section-documents", "nav-documents")
    }
  }

  const navProject = document.getElementById("nav-project")
  if (navProject) {
    navProject.onclick = (e) => {
      e.preventDefault()
      showSection("section-project", "nav-project")
    }
  }

  const navOngoingProjects = document.getElementById("nav-ongoing-projects")
  if (navOngoingProjects) {
    navOngoingProjects.onclick = (e) => {
      e.preventDefault()
      showSection("section-ongoing-projects", "nav-ongoing-projects")
      displayOngoingProjects()
    }
  }

  const navIrn = document.getElementById("nav-irn")
  if (navIrn) {
    navIrn.onclick = (e) => {
      e.preventDefault()
      showSection("section-irn", "nav-irn")
    }
  }

  // Next/Prev Button Logic
  const toCompanyInfo = document.getElementById("toCompanyInfo")
  if (toCompanyInfo) {
    toCompanyInfo.onclick = () => {
      const clientForm = document.getElementById("clientInfoForm")
      if (clientForm && clientForm.reportValidity()) {
        showSection("section-company-info", "nav-company-info")
      }
    }
  }

  const backToClientInfo = document.getElementById("backToClientInfo")
  if (backToClientInfo) {
    backToClientInfo.onclick = () => {
      showSection("section-client-info", "nav-client-info")
    }
  }

  const backToCompanyInfo = document.getElementById("backToCompanyInfo")
  if (backToCompanyInfo) {
    backToCompanyInfo.onclick = () => {
      showSection("section-company-info", "nav-company-info")
    }
  }

  const backToDocuments = document.getElementById("backToDocuments")
  if (backToDocuments) {
    backToDocuments.onclick = () => {
      showSection("section-documents", "nav-documents")
    }
  }
}

// --- Form Handlers Initialization ---
function initializeFormHandlers() {
  // Registration handler
  const toDocuments = document.getElementById("toDocuments")
  if (toDocuments) {
    toDocuments.onclick = handleClientRegistration
  }
}

// --- MSME Handlers ---
function initializeMSMEHandlers() {
  const msmeSelect = document.getElementById("msme")
  if (msmeSelect) {
    msmeSelect.onchange = function () {
      const show = this.value === "yes"
      const udyamRow = document.getElementById("udyamRow")
      const udyamDocRow = document.getElementById("udyamDocRow")
      const udyamInput = document.getElementById("udyam")

      if (udyamRow) udyamRow.style.display = show ? "" : "none"
      if (udyamDocRow) udyamDocRow.style.display = show ? "" : "none"
      if (udyamInput) udyamInput.required = show
    }
  }

  const gstinInput = document.getElementById("gstin")
  if (gstinInput) {
    gstinInput.oninput = function () {
      const hasGstin = this.value.trim().length > 0
      const gstDocRow = document.getElementById("gstDocRow")
      const gstDocInput = document.getElementById("gstDoc")

      if (gstDocRow) gstDocRow.style.display = hasGstin ? "" : "none"
      if (gstDocInput) gstDocInput.required = hasGstin
    }
  }
}

// --- Document Upload Initialization ---
function initializeDocumentUpload() {
  const documentsForm = document.getElementById("documentsForm")
  if (documentsForm) {
    documentsForm.onsubmit = handleDocumentUpload
  }
}

// --- Project Handlers ---
function initializeProjectHandlers() {
  // Service request form
  const serviceRequestForm = document.getElementById("serviceRequestForm")
  if (serviceRequestForm) {
    serviceRequestForm.onsubmit = handleServiceRequest
  }

  // Project tabs
  const requestTab = document.getElementById("project-request-tab")
  const matchesTab = document.getElementById("project-matches-tab")
  const requestContent = document.getElementById("project-request")
  const matchesContent = document.getElementById("project-matches")

  if (requestTab && matchesTab && requestContent && matchesContent) {
    requestTab.onclick = () => {
      requestTab.classList.add("active")
      matchesTab.classList.remove("active")
      requestContent.classList.add("active")
      matchesContent.classList.remove("active")
    }
    matchesTab.onclick = () => {
      matchesTab.classList.add("active")
      requestTab.classList.remove("active")
      matchesContent.classList.add("active")
      requestContent.classList.remove("active")
    }
  }

  // Populate matches
  populateMatches()
}

// --- Client Registration Handler ---
async function handleClientRegistration() {
  const companyForm = document.getElementById("companyInfoForm")
  const clientForm = document.getElementById("clientInfoForm")

  if (!companyForm || !clientForm) {
    alert("Forms not found")
    return
  }

  if (!companyForm.reportValidity()) return

  console.log("=== Registration Debug ===")

  const payload = {
    clientInfo: {
      fullName: clientForm.fullName.value,
      designation: clientForm.designation.value,
      officialEmail: clientForm.officialEmail.value,
      mobileNumber: clientForm.mobile.value,
      alternateContact: clientForm.altContact.value,
    },
    companyInfo: {
      companyName: companyForm.companyName.value,
      industryType: companyForm.industryType.value,
      companyAddress: companyForm.companyAddress.value,
      officialEmail: companyForm.companyEmail.value,
      yearOfEstablishment: Number.parseInt(companyForm.estYear.value),
      isMsme: companyForm.msme.value,
      gstin: companyForm.gstin.value,
      website: companyForm.website.value,
    },
  }

  console.log("Registration payload:", payload)

  try {
    const res = await fetch("http://localhost:9090/api/client/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    })

    console.log("Registration response status:", res.status)

    if (!res.ok) {
      const errorText = await res.text()
      console.log("Registration error:", errorText)
      throw new Error("Server error: " + errorText)
    }

    const responseData = await res.json()
    console.log("Registration success:", responseData)

    currentCompanyId = responseData.companyId
    console.log("Company ID set to:", currentCompanyId)

    // Set the companyId in the hidden input
    const hiddenInput = document.getElementById("companyId")
    if (hiddenInput) {
      hiddenInput.value = currentCompanyId
      console.log("Hidden input set to:", hiddenInput.value)
    }

    alert("Client information saved! Company ID: " + currentCompanyId)
    showSection("section-documents", "nav-documents")
  } catch (err) {
    console.error("Registration error:", err)
    alert("Failed to submit client information: " + err.message)
  }
}

// --- Document Upload Handler ---
async function handleDocumentUpload(e) {
  e.preventDefault()
  console.log("=== Document Upload Debug ===")

  const companyId = currentCompanyId || document.getElementById("companyId")?.value

  if (!companyId) {
    alert("Company ID is missing. Please complete the registration first.")
    return
  }

  const panCardFile = document.getElementById("panCard")?.files[0]
  const registrationProofFile = document.getElementById("registrationProof")?.files[0]
  const workOrderFile = document.getElementById("workOrder")?.files[0]

  if (!panCardFile || !registrationProofFile) {
    alert("Please select required files")
    return
  }

  const formData = new FormData()
  formData.append("companyId", String(companyId))
  formData.append("panCard", panCardFile)
  formData.append("registrationProof", registrationProofFile)
  if (workOrderFile) {
    formData.append("workOrder", workOrderFile)
  }

  try {
    const res = await fetch("http://localhost:9090/api/client/documents/upload", {
      method: "POST",
      body: formData,
    })

    if (!res.ok) {
      const errorText = await res.text()
      throw new Error(`Server error (${res.status}): ${errorText}`)
    }

    const responseData = await res.json()
    alert(responseData.message || "Documents uploaded successfully")
    showSection("section-project", "nav-project")
    e.target.reset()
  } catch (err) {
    console.error("Upload error:", err)
    alert("Failed to upload documents: " + err.message)
  }
}

// --- Service Request Handler ---
async function handleServiceRequest(e) {
  e.preventDefault()
  console.log("=== Project Creation Debug ===")

  const form = e.target
  if (!form.reportValidity()) return

  const companyId = currentCompanyId || document.getElementById("companyId")?.value

  if (!companyId) {
    alert("Company ID is missing. Please complete the registration first.")
    return
  }

  const formData = new FormData(form)
  const preferredVendorsSelect = document.getElementById("preferredVendors")
  const selectedVendors = preferredVendorsSelect
    ? Array.from(preferredVendorsSelect.selectedOptions).map((option) => option.value)
    : []

  const projectData = {
    projectTitle: formData.get("projectTitle"),
    projectDescription: formData.get("projectDesc"),
    serviceCategory: formData.get("serviceCategory"),
    startDate: formData.get("startDate"),
    endDate: formData.get("endDate"),
    projectLocation: formData.get("location"),
    budget: formData.get("budget") ? Number.parseFloat(formData.get("budget")) : null,
    priority: formData.get("priority"),
    preferredVendors: selectedVendors,
    companyId: Number.parseInt(companyId),
  }

  console.log("Project data to send:", projectData)

  try {
    const res = await fetch("http://localhost:9090/api/client/projects/create", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(projectData),
    })

    if (!res.ok) {
      const errorText = await res.text()
      throw new Error(errorText)
    }

    const responseData = await res.json()
    console.log("Project creation success:", responseData)

    const status = document.getElementById("serviceRequestStatus")
    if (status) {
      status.textContent = responseData.message + " (Project ID: " + responseData.projectId + ")"
      status.style.display = "block"
      status.style.color = "#16a34a"

      setTimeout(() => {
        status.style.display = "none"
      }, 3000)
    }

    form.reset()
  } catch (err) {
    console.error("Project creation error:", err)
    alert("Failed to create project: " + err.message)
  }
}

// --- Populate Matches ---
function populateMatches() {
  const matchedVendors = [
    { name: "Vendor A", expertise: "Mechanical Inspection", contact: "vendora@vendors.com" },
    { name: "Vendor B", expertise: "Electrical QA", contact: "vendorb@vendors.com" },
    { name: "Vendor C", expertise: "Civil Engineering", contact: "vendorc@vendors.com" },
  ]

  const matchesList = document.getElementById("matchedVendorsList")
  if (matchesList) {
    matchesList.innerHTML = matchedVendors
      .map(
        (v) =>
          `<li style="margin-bottom:9px;"><strong>${v.name}</strong> <br/><span style="color:#002e5d">${v.expertise}</span><br/>
        <a href="mailto:${v.contact}">${v.contact}</a></li>`,
      )
      .join("")
  }
}

// --- Ongoing Projects ---
function initializeOngoingProjects() {
  const ongoingProjectSelect = document.getElementById("ongoingProjectSelect")
  if (ongoingProjectSelect) {
    ongoingProjectSelect.addEventListener("change", handleProjectSelection)
  }
}

function handleProjectSelection() {
  const ongoingProjectSelect = document.getElementById("ongoingProjectSelect")
  const ceilTableDynamicContainer = document.getElementById("ceil-table-dynamic-container")

  if (!ongoingProjectSelect || !ceilTableDynamicContainer) return

  if (!ongoingProjectSelect.value) {
    ceilTableDynamicContainer.style.display = "none"
    return
  }

  const selectedOption = ongoingProjectSelect.options[ongoingProjectSelect.selectedIndex]
  const projectData = selectedOption.dataset.project

  if (projectData) {
    try {
      const project = JSON.parse(projectData)
      displayProjectDetails(project)
    } catch (e) {
      console.error("Error parsing project data:", e)
    }
  }
}

function displayProjectDetails(project) {
  const ceilTableDynamicContainer = document.getElementById("ceil-table-dynamic-container")
  if (!project || !ceilTableDynamicContainer) return

  const startDate = project.startDate ? new Date(project.startDate).toLocaleDateString() : "N/A"
  const endDate = project.endDate ? new Date(project.endDate).toLocaleDateString() : "N/A"
  const budget = project.budget ? `₹${project.budget.toLocaleString()}` : "Not specified"

  ceilTableDynamicContainer.innerHTML = `
    <div class="ceil-table-container">
      <div class="ceil-table-title">Project Details: ${project.projectTitle}</div>
      <div style="padding: 20px; background: #f9f9f9; border-radius: 8px; margin: 10px 0;">
        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 15px;">
          <div><strong>Service Category:</strong> ${project.serviceCategory}</div>
          <div><strong>Status:</strong> ${project.status}</div>
          <div><strong>Start Date:</strong> ${startDate}</div>
          <div><strong>End Date:</strong> ${endDate}</div>
          <div><strong>Location:</strong> ${project.projectLocation}</div>
          <div><strong>Budget:</strong> ${budget}</div>
          <div><strong>Priority:</strong> ${project.priority}</div>
        </div>
      </div>
    </div>
  `
  ceilTableDynamicContainer.style.display = ""
}

async function displayOngoingProjects() {
  const companyId = currentCompanyId || document.getElementById("companyId")?.value

  if (!companyId) {
    console.log("No company ID available for loading projects")
    return
  }

  try {
    const res = await fetch(`http://localhost:9090/api/client/projects/company/${companyId}`)
    if (!res.ok) throw new Error("Failed to load projects")

    const data = await res.json()
    const projects = data.projects || []

    const projectSelect = document.getElementById("ongoingProjectSelect")
    if (projectSelect) {
      projectSelect.innerHTML = '<option value="">Select Project</option>'
      projects.forEach((project) => {
        const option = document.createElement("option")
        option.value = project.id
        option.textContent = `${project.projectTitle} (${project.status})`
        option.dataset.project = JSON.stringify(project)
        projectSelect.appendChild(option)
      })
    }
  } catch (err) {
    console.error("Error loading projects:", err)
  }
}

// --- IRN Table ---
function renderIrnTable() {
  const irnData = [
    { irn: "IRN-10001", date: "2025-06-01", amount: "15200.50", status: "Verified", remarks: "Invoice approved" },
    { irn: "IRN-10002", date: "2025-06-05", amount: "9800.00", status: "Submitted", remarks: "Awaiting verification" },
  ]

  const irnTable = document.getElementById("irnTableBody")
  if (irnTable) {
    irnTable.innerHTML = ""
    irnData.forEach((irn, index) => {
      const row = document.createElement("tr")
      row.innerHTML = `
        <td>${index + 1}</td>
        <td>${irn.irn}</td>
        <td>${irn.date}</td>
        <td>₹${irn.amount}</td>
        <td>${irn.status}</td>
        <td>${irn.remarks}</td>
      `
      irnTable.appendChild(row)
    })
  }
}

// --- Support Modal ---
function initializeSupportModal() {
  const openSupportModal = document.getElementById("openSupportModal")
  const closeSupportModal = document.getElementById("closeSupportModal")
  const supportModal = document.getElementById("supportModal")
  const supportForm = document.getElementById("supportForm")

  if (openSupportModal && supportModal) {
    openSupportModal.onclick = () => {
      supportModal.style.display = "flex"
    }
  }

  if (closeSupportModal && supportModal) {
    closeSupportModal.onclick = () => {
      supportModal.style.display = "none"
    }
  }

  if (supportForm) {
    supportForm.onsubmit = function (e) {
      e.preventDefault()
      const status = document.getElementById("supportFormStatus")
      if (status) {
        status.textContent = "Your query has been submitted. Our support team will contact you soon!"
        status.style.display = "block"
        setTimeout(() => {
          status.style.display = "none"
          if (supportModal) supportModal.style.display = "none"
          this.reset()
        }, 2500)
      }
    }
  }

  window.onclick = (event) => {
    if (event.target === supportModal) {
      supportModal.style.display = "none"
    }
  }
}
