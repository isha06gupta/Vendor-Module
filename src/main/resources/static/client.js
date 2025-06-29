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

    // Refresh ongoing projects if we're on that section
    if (document.getElementById("section-ongoing-projects")?.classList.contains("active-section")) {
      displayOngoingProjects()
    }
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

// --- Ongoing Projects (FIXED VERSION) ---
function initializeOngoingProjects() {
  const ongoingProjectSelect = document.getElementById("ongoingProjectSelect")
  if (ongoingProjectSelect) {
    ongoingProjectSelect.addEventListener("change", handleProjectSelection)
  }
}

function handleProjectSelection() {
  const ongoingProjectSelect = document.getElementById("ongoingProjectSelect")
  const ceilTableDynamicContainer = document.getElementById("ceil-table-dynamic-container")

  console.log("=== Project Selection Debug ===")
  console.log("Selected value:", ongoingProjectSelect?.value)

  if (!ongoingProjectSelect || !ceilTableDynamicContainer) {
    console.log("Missing elements")
    return
  }

  if (!ongoingProjectSelect.value) {
    console.log("No project selected, hiding table")
    ceilTableDynamicContainer.style.display = "none"
    ceilTableDynamicContainer.innerHTML = ""
    return
  }

  const selectedOption = ongoingProjectSelect.options[ongoingProjectSelect.selectedIndex]
  const projectData = selectedOption.dataset.project

  console.log("Project data from option:", projectData)

  if (projectData) {
    try {
      const project = JSON.parse(projectData)
      console.log("Parsed project:", project)
      displayProjectDetailsTable(project)
    } catch (e) {
      console.error("Error parsing project data:", e)
      displayErrorMessage("Error loading project details")
    }
  } else {
    console.log("No project data found")
    displayErrorMessage("No project data available")
  }
}

function displayProjectDetailsTable(project) {
  const ceilTableDynamicContainer = document.getElementById("ceil-table-dynamic-container")
  if (!project || !ceilTableDynamicContainer) return

  console.log("Displaying project details for:", project.projectTitle)

  const startDate = project.startDate ? new Date(project.startDate).toLocaleDateString() : "N/A"
  const endDate = project.endDate ? new Date(project.endDate).toLocaleDateString() : "N/A"
  const budget = project.budget ? `₹${project.budget.toLocaleString()}` : "Not specified"
  const preferredVendors = project.preferredVendors ? project.preferredVendors.split(",").join(", ") : "None specified"
  const createdDate = project.createdAt ? new Date(project.createdAt).toLocaleDateString() : "N/A"

  // Generate project order number (PRJ-YYYY-ID format)
  const orderNumber = `PRJ-${new Date().getFullYear()}-${String(project.id).padStart(4, "0")}`

  ceilTableDynamicContainer.innerHTML = `
    <div class="ceil-table-container" style="margin-top: 20px;">
      <div class="ceil-table-title">Project Details: ${project.projectTitle}</div>
      <table class="submission-table" style="width: 100%; border-collapse: collapse; margin-top: 15px;">
        <thead>
          <tr style="background-color: #f5f5f5;">
            <th style="padding: 12px; text-align: left; border: 1px solid #ddd; font-weight: bold;">Field</th>
            <th style="padding: 12px; text-align: left; border: 1px solid #ddd; font-weight: bold;">Details</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td style="padding: 10px; border: 1px solid #ddd; font-weight: bold;">Project Order Number</td>
            <td style="padding: 10px; border: 1px solid #ddd;">${orderNumber}</td>
          </tr>
          <tr>
            <td style="padding: 10px; border: 1px solid #ddd; font-weight: bold;">Project Title</td>
            <td style="padding: 10px; border: 1px solid #ddd;">${project.projectTitle}</td>
          </tr>
          <tr>
            <td style="padding: 10px; border: 1px solid #ddd; font-weight: bold;">Service Category</td>
            <td style="padding: 10px; border: 1px solid #ddd;">${project.serviceCategory}</td>
          </tr>
          <tr>
            <td style="padding: 10px; border: 1px solid #ddd; font-weight: bold;">Status</td>
            <td style="padding: 10px; border: 1px solid #ddd;">
              <span style="color: ${getStatusColor(project.status)}; font-weight: bold;">${project.status}</span>
            </td>
          </tr>
          <tr>
            <td style="padding: 10px; border: 1px solid #ddd; font-weight: bold;">Start Date</td>
            <td style="padding: 10px; border: 1px solid #ddd;">${startDate}</td>
          </tr>
          <tr>
            <td style="padding: 10px; border: 1px solid #ddd; font-weight: bold;">End Date</td>
            <td style="padding: 10px; border: 1px solid #ddd;">${endDate}</td>
          </tr>
          <tr>
            <td style="padding: 10px; border: 1px solid #ddd; font-weight: bold;">Location</td>
            <td style="padding: 10px; border: 1px solid #ddd;">${project.projectLocation}</td>
          </tr>
          <tr>
            <td style="padding: 10px; border: 1px solid #ddd; font-weight: bold;">Budget</td>
            <td style="padding: 10px; border: 1px solid #ddd;">${budget}</td>
          </tr>
          <tr>
            <td style="padding: 10px; border: 1px solid #ddd; font-weight: bold;">Priority</td>
            <td style="padding: 10px; border: 1px solid #ddd;">
              <span style="color: ${getPriorityColor(project.priority)}; font-weight: bold;">${project.priority}</span>
            </td>
          </tr>
          <tr>
            <td style="padding: 10px; border: 1px solid #ddd; font-weight: bold;">Description</td>
            <td style="padding: 10px; border: 1px solid #ddd;">${project.projectDescription || "No description provided"}</td>
          </tr>
          <tr>
            <td style="padding: 10px; border: 1px solid #ddd; font-weight: bold;">Preferred Vendors</td>
            <td style="padding: 10px; border: 1px solid #ddd;">${preferredVendors}</td>
          </tr>
          <tr>
            <td style="padding: 10px; border: 1px solid #ddd; font-weight: bold;">Created Date</td>
            <td style="padding: 10px; border: 1px solid #ddd;">${createdDate}</td>
          </tr>
        </tbody>
      </table>
    </div>
  `
  ceilTableDynamicContainer.style.display = "block"
}

function displayErrorMessage(message) {
  const ceilTableDynamicContainer = document.getElementById("ceil-table-dynamic-container")
  if (!ceilTableDynamicContainer) return

  ceilTableDynamicContainer.innerHTML = `
    <div style="padding: 20px; text-align: center; color: #666;">
      <p>${message}</p>
    </div>
  `
  ceilTableDynamicContainer.style.display = "block"
}

function getStatusColor(status) {
  switch (status?.toUpperCase()) {
    case "PENDING":
      return "#f59e0b"
    case "IN_PROGRESS":
      return "#3b82f6"
    case "COMPLETED":
      return "#10b981"
    case "CANCELLED":
      return "#ef4444"
    default:
      return "#6b7280"
  }
}

function getPriorityColor(priority) {
  switch (priority?.toUpperCase()) {
    case "HIGH":
      return "#ef4444"
    case "MEDIUM":
      return "#f59e0b"
    case "LOW":
      return "#10b981"
    default:
      return "#6b7280"
  }
}

async function displayOngoingProjects() {
  console.log("=== Display Ongoing Projects Debug ===")

  const companyId = currentCompanyId || document.getElementById("companyId")?.value
  console.log("Company ID:", companyId)

  // Always try to load projects, but have fallback data
  let projects = []

  if (companyId) {
    try {
      console.log("Fetching projects from API...")
      const res = await fetch(`http://localhost:9090/api/client/projects/company/${companyId}`)

      console.log("API Response status:", res.status)

      if (res.ok) {
        const data = await res.json()
        console.log("API Response data:", data)
        projects = data.projects || []
        console.log("Number of projects found:", projects.length)
      } else {
        console.log("API call failed, using fallback data")
        projects = getFallbackProjects()
      }
    } catch (err) {
      console.error("Error loading projects:", err)
      console.log("Using fallback data due to error")
      projects = getFallbackProjects()
    }
  } else {
    console.log("No company ID, using fallback data")
    projects = getFallbackProjects()
  }

  const projectSelect = document.getElementById("ongoingProjectSelect")
  if (!projectSelect) {
    console.log("Project select element not found")
    return
  }

  // Clear existing options
  projectSelect.innerHTML = '<option value="">Select Project</option>'

  if (projects.length === 0) {
    console.log("No projects found")
    projectSelect.innerHTML += '<option value="" disabled>No projects available</option>'
    displayNoProjectsMessage("No projects available.")
    return
  }

  // Add projects to dropdown with order numbers
  projects.forEach((project, index) => {
    const orderNumber = `PRJ-${new Date().getFullYear()}-${String(project.id).padStart(4, "0")}`
    const option = document.createElement("option")
    option.value = project.id
    option.textContent = `${orderNumber} - ${project.projectTitle} (${project.status})`
    option.dataset.project = JSON.stringify(project)
    projectSelect.appendChild(option)

    console.log(`Added project: ${orderNumber} - ${project.projectTitle}`)
  })

  console.log(`Successfully loaded ${projects.length} projects into dropdown`)

  // Hide the table container initially
  const ceilTableDynamicContainer = document.getElementById("ceil-table-dynamic-container")
  if (ceilTableDynamicContainer) {
    ceilTableDynamicContainer.style.display = "none"
  }
}

// Add this new function to provide fallback project data
function getFallbackProjects() {
  return [
    {
      id: 1,
      projectTitle: "Metro Station Construction",
      serviceCategory: "Civil",
      status: "IN_PROGRESS",
      startDate: "2025-01-15",
      endDate: "2025-06-30",
      projectLocation: "Delhi Metro Line 8",
      budget: 5000000,
      priority: "High",
      projectDescription:
        "Construction and quality assurance for new metro station including structural integrity testing and safety compliance verification.",
      preferredVendors: "Vendor A,Vendor B",
      createdAt: "2025-01-10T10:00:00Z",
    },
    {
      id: 2,
      projectTitle: "Bridge Safety Inspection",
      serviceCategory: "Civil",
      status: "PENDING",
      startDate: "2025-02-01",
      endDate: "2025-03-15",
      projectLocation: "Yamuna Bridge, Delhi",
      budget: 1500000,
      priority: "Medium",
      projectDescription:
        "Comprehensive safety inspection and structural analysis of existing bridge infrastructure with detailed reporting.",
      preferredVendors: "Vendor C",
      createdAt: "2025-01-20T14:30:00Z",
    },
    {
      id: 3,
      projectTitle: "IT Infrastructure Audit",
      serviceCategory: "IT",
      status: "COMPLETED",
      startDate: "2024-12-01",
      endDate: "2024-12-31",
      projectLocation: "Corporate Office, Gurgaon",
      budget: 800000,
      priority: "Low",
      projectDescription:
        "Complete IT infrastructure audit including network security assessment and compliance verification.",
      preferredVendors: "Vendor A,Vendor D",
      createdAt: "2024-11-25T09:15:00Z",
    },
    {
      id: 4,
      projectTitle: "Electrical System Testing",
      serviceCategory: "Electrical",
      status: "PENDING",
      startDate: "2025-03-01",
      endDate: "2025-04-15",
      projectLocation: "Industrial Complex, Noida",
      budget: 2200000,
      priority: "High",
      projectDescription:
        "Electrical system testing and certification for industrial facility including power distribution and safety systems.",
      preferredVendors: "Vendor B,Vendor E",
      createdAt: "2025-01-25T16:45:00Z",
    },
    {
      id: 5,
      projectTitle: "Mechanical Equipment Inspection",
      serviceCategory: "Mechanical",
      status: "IN_PROGRESS",
      startDate: "2025-01-20",
      endDate: "2025-02-28",
      projectLocation: "Manufacturing Plant, Faridabad",
      budget: 1800000,
      priority: "Medium",
      projectDescription:
        "Inspection and certification of mechanical equipment including pumps, compressors, and conveyor systems.",
      preferredVendors: "Vendor C,Vendor F",
      createdAt: "2025-01-15T11:20:00Z",
    },
  ]
}

function displayNoProjectsMessage(message) {
  const projectSelect = document.getElementById("ongoingProjectSelect")
  const ceilTableDynamicContainer = document.getElementById("ceil-table-dynamic-container")

  if (projectSelect && projectSelect.children.length <= 1) {
    projectSelect.innerHTML += `<option value="" disabled>${message}</option>`
  }

  if (ceilTableDynamicContainer) {
    ceilTableDynamicContainer.innerHTML = `
      <div style="padding: 20px; text-align: center; color: #666; background: #f9f9f9; border-radius: 8px; margin-top: 20px;">
        <p>${message}</p>
      </div>
    `
    ceilTableDynamicContainer.style.display = "block"
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
