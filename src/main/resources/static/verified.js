// Global variable to store current vendor ID
const currentVendorId = localStorage.getItem("currentVendorId") || "1" // Default to 1 for testing

// Sample Data for all projects (keeping your existing structure)
const projectData = {
  "Order-101": {
    dashboard: {
      Name: "Ravi Kumar",
      "Date of Birth": "1985-07-20",
      Contact: "9876543210",
      Phone: "+91-9876543210",
      Email: "ravi.kumar@example.com",
      Address: "101 Main Street, Delhi",
      GSTIN: "29ABCDE1234F2Z5",
      "Are you registered as MSME?": "Yes",
    },
    companyProfile: {
      "Company Name": "ABC EPC Pvt Ltd",
      "Registration Number / CIN": "U12345DL2010PTC123456",
      "Date of Incorporation / Registration": "2010-03-15",
      "Type of Company": "Private Limited",
      "Industry Type / Sector": "Engineering",
      "Office Address": "Plot 5, Sector 1, Delhi",
      Email: "info@abcepc.com",
      "Phone Number": "+91-1122334455",
      "Website URL": "www.abcepc.com",
      "PAN Number": "ABCDE1234F",
      "GST Number": "29ABCDE1234F2Z5",
      "MSME / Udyam Registration No.": "UDYAM-DL-12-0001234",
      "ISO Certification": "ISO 9001:2015",
      "Other Licenses / Approvals": "-",
    },
    projectInfo: {
      "Project Name": "Delhi Metro Expansion",
      "Project Code / ID": "DEL-METRO-101",
      "Client / Customer Name": "DMRC",
      "Location (City, State)": "Delhi, Delhi",
      "Type of Project": "Metro Rail",
      "Start Date": "2024-01-01",
      "Expected Completion Date": "2026-12-31",
      Status: "In Progress",
      "Last Updated On": "2025-06-12",
      "Brief Project Description": "Expansion of Delhi Metro Blue Line.",
      "Project Manager Name": "Neha Gupta",
      "Project Team Members / Departments Involved": "Civil, Electrical, Procurement",
      "Assigned Vendor(s)": "XYZ Vendors",
      "Total Project Budget": "₹120 Crore",
      "Project Agreement / Contract (PDF)": "agreement101.pdf",
      "Work Order documents": "workorder101.pdf",
      "Compliance certificates (if any)": "safety_cert101.pdf",
      "Linked Vendor(s)": "XYZ Vendors",
      "Linked CEIL Employee(s)": "Anil Sharma, Priya Singh",
      "Linked Company": "ABC EPC Pvt Ltd",
    },
    submissions: [
      {
        type: "Document Upload",
        fileName: "metro_plan_101.pdf",
        link: "#",
        submittedBy: "Ravi Kumar",
        date: "2025-06-12",
        status: "Approved",
        notes: "—",
      },
    ],
    queries: [
      {
        date: "2025-06-12",
        raisedBy: "Priya Singh (Vendor Manager)",
        title: "Clarification on safety norms",
        description: "Please clarify if the June safety standard applies to all subcontractors or only selected ones.",
        assignedTo: "Rohit Kapoor",
        status: "Responded",
        response: "Yes, the June safety standard is applicable to all subcontractors.",
        responseDate: "2025-06-12",
        notes: "—",
        attachment: "safety_guidelines.pdf",
      },
    ],
  },
  "Order-102": {
    dashboard: {
      Name: "Isha Gupta",
      "Date of Birth": "1990-08-15",
      Contact: "9123456789",
      Phone: "+91-9123456789",
      Email: "isha.gupta@example.com",
      Address: "202 Main Street, Mumbai",
      GSTIN: "27ABCDE5678J9Z7",
      "Are you registered as MSME?": "No",
    },
    companyProfile: {
      "Company Name": "XYZ Infra Ltd",
      "Registration Number / CIN": "U54321MH2012PLC543210",
      "Date of Incorporation / Registration": "2012-06-10",
      "Type of Company": "Public Limited",
      "Industry Type / Sector": "Infrastructure",
      "Office Address": "Block A, Sector 4, Mumbai",
      Email: "contact@xyzinfra.com",
      "Phone Number": "+91-9988776655",
      "Website URL": "www.xyzinfra.com",
      "PAN Number": "ABCDE5678J",
      "GST Number": "27ABCDE5678J9Z7",
      "MSME / Udyam Registration No.": "—",
      "ISO Certification": "ISO 14001:2015",
      "Other Licenses / Approvals": "Environmental Clearance",
    },
    projectInfo: {
      "Project Name": "Mumbai Flyover Phase 2",
      "Project Code / ID": "MUM-FLY-202",
      "Client / Customer Name": "MMRDA",
      "Location (City, State)": "Mumbai, Maharashtra",
      "Type of Project": "Flyover",
      "Start Date": "2023-04-01",
      "Expected Completion Date": "2025-10-15",
      Status: "Near Completion",
      "Last Updated On": "2025-06-13",
      "Brief Project Description": "Construction of flyover connecting Eastern and Western Express Highway.",
      "Project Manager Name": "Suresh Patel",
      "Project Team Members / Departments Involved": "Civil, Architecture",
      "Assigned Vendor(s)": "InfraBuild Pvt Ltd",
      "Total Project Budget": "₹80 Crore",
      "Project Agreement / Contract (PDF)": "agreement102.pdf",
      "Work Order documents": "workorder102.pdf",
      "Compliance certificates (if any)": "environmental_cert102.pdf",
      "Linked Vendor(s)": "InfraBuild Pvt Ltd",
      "Linked CEIL Employee(s)": "Sunil Mehta, Anita Rao",
      "Linked Company": "XYZ Infra Ltd",
    },
    submissions: [
      {
        type: "Document Upload",
        fileName: "flyover_design_102.pdf",
        link: "#",
        submittedBy: "Isha Gupta",
        date: "2025-05-10",
        status: "Pending",
        notes: "Pending review",
      },
      {
        type: "Invoice Submission",
        fileName: "invoice_102.pdf",
        link: "#",
        submittedBy: "XYZ Infra Ltd",
        date: "2025-05-12",
        status: "Approved",
        notes: "—",
      },
    ],
    queries: [
      {
        date: "2025-06-13",
        raisedBy: "Sunil Mehta (Project Lead)",
        title: "Request for extension",
        description: "Can we get an extension for the milestone due to supply chain delays?",
        assignedTo: "Anita Rao",
        status: "Pending",
        response: "—",
        responseDate: "—",
        notes: "Escalated to management for review.",
        attachment: "—",
      },
    ],
  },
  "Order-103": {
    dashboard: {
      Name: "Amit Verma",
      "Date of Birth": "1988-11-30",
      Contact: "9988776655",
      Phone: "+91-9988776655",
      Email: "amit.verma@example.com",
      Address: "303 Main Street, Bangalore",
      GSTIN: "29ABCDE9876L5Z3",
      "Are you registered as MSME?": "Yes",
    },
    companyProfile: {
      "Company Name": "LMN Projects LLP",
      "Registration Number / CIN": "AAB-1234",
      "Date of Incorporation / Registration": "2015-09-25",
      "Type of Company": "LLP",
      "Industry Type / Sector": "Construction",
      "Office Address": "Tower B, Sector 5, Bangalore",
      Email: "support@lmnprojects.com",
      "Phone Number": "+91-8877665544",
      "Website URL": "www.lmnprojects.com",
      "PAN Number": "ABCDE9876L",
      "GST Number": "29ABCDE9876L5Z3",
      "MSME / Udyam Registration No.": "UDYAM-KA-29-0005678",
      "ISO Certification": "ISO 45001:2018",
      "Other Licenses / Approvals": "Labour License",
    },
    projectInfo: {
      "Project Name": "Bangalore IT Park",
      "Project Code / ID": "BLR-IT-303",
      "Client / Customer Name": "Karnataka IT Board",
      "Location (City, State)": "Bangalore, Karnataka",
      "Type of Project": "IT Park",
      "Start Date": "2022-09-01",
      "Expected Completion Date": "2025-11-01",
      Status: "In Progress",
      "Last Updated On": "2025-06-11",
      "Brief Project Description": "Development of IT infrastructure in Bangalore.",
      "Project Manager Name": "Manoj Rao",
      "Project Team Members / Departments Involved": "IT, Civil, HR",
      "Assigned Vendor(s)": "BuildTech Ltd",
      "Total Project Budget": "₹150 Crore",
      "Project Agreement / Contract (PDF)": "agreement103.pdf",
      "Work Order documents": "workorder103.pdf",
      "Compliance certificates (if any)": "iso_cert103.pdf",
      "Linked Vendor(s)": "BuildTech Ltd",
      "Linked CEIL Employee(s)": "Kavita Joshi, Anuj Kumar",
      "Linked Company": "LMN Projects LLP",
    },
    submissions: [
      {
        type: "Document Upload",
        fileName: "itpark_layout_103.pdf",
        link: "#",
        submittedBy: "Amit Verma",
        date: "2025-04-20",
        status: "Approved",
        notes: "—",
      },
    ],
    queries: [],
  },
}

// Load vendor data from backend and merge with existing data
async function loadVendorData() {
  console.log("=== Loading Vendor Data ===")
  console.log("Current vendor ID:", currentVendorId)

  if (!currentVendorId) {
    console.log("No vendor ID found, using fallback data")
    return
  }

  try {
    // Fetch vendor personal info
    const personalInfoResponse = await fetch(`http://localhost:9090/api/vendor/personal-info/${currentVendorId}`)

    if (personalInfoResponse.ok) {
      const personalInfo = await personalInfoResponse.json()
      console.log("Loaded personal info:", personalInfo)

      // Update dashboard data for all projects with real data
      const dashboardData = {
        Name: personalInfo.name || "N/A",
        "Date of Birth": personalInfo.dateOfBirth || "N/A",
        "Contact Person": personalInfo.contactPerson || "N/A",
        Phone: personalInfo.phone || "N/A",
        Email: personalInfo.email || "N/A",
        Address: personalInfo.address || "N/A",
        GSTIN: personalInfo.gstin || "N/A",
        "Are you registered as MSME?": personalInfo.isMsme || "N/A",
      }

      // Update all projects with this real data
      Object.keys(projectData).forEach((projectKey) => {
        projectData[projectKey].dashboard = dashboardData
      })

      console.log("Updated project data with real vendor info")
    } else {
      console.log("Failed to load personal info, using fallback data")
    }

    // Fetch vendor documents (optional)
    try {
      const documentsResponse = await fetch(`http://localhost:9090/api/vendor/documents/${currentVendorId}`)
      if (documentsResponse.ok) {
        const documents = await documentsResponse.json()
        console.log("Loaded documents:", documents)
      }
    } catch (docError) {
      console.log("Documents not found or error loading documents")
    }
  } catch (error) {
    console.error("Error loading vendor data:", error)
    console.log("Using fallback data due to error")
  }
}

function fillDetailsGrid(gridId, dataObj) {
  const grid = document.getElementById(gridId)
  if (!grid) return

  grid.innerHTML = ""
  for (const [key, value] of Object.entries(dataObj)) {
    const row = document.createElement("div")
    row.className = "details-row"
    row.innerHTML = `<strong>${key}:</strong> <span>${value}</span>`
    grid.appendChild(row)
  }
}

function fillSubmissionHistoryTable(submissions) {
  const tbody = document.querySelector("#submissionHistoryTable tbody")
  if (!tbody) return

  tbody.innerHTML = ""
  submissions.forEach((sub, i) => {
    const tr = document.createElement("tr")
    tr.innerHTML = `
      <td>${i + 1}</td>
      <td>${sub.type}</td>
      <td>${sub.fileName}</td>
      <td><a href="${sub.link}" target="_blank">${sub.type === "Invoice Submission" ? "Download" : "View"}</a></td>
      <td>${sub.submittedBy}</td>
      <td>${sub.date}</td>
      <td>${sub.status}</td>
      <td>${sub.notes}</td>
    `
    tbody.appendChild(tr)
  })
}

function fillQueriesResponsesTable(queries) {
  const tbody = document.querySelector("#queriesResponsesTable tbody")
  if (!tbody) return

  tbody.innerHTML = ""
  queries.forEach((q, i) => {
    const tr = document.createElement("tr")
    tr.innerHTML = `
      <td>${i + 1}</td>
      <td>${q.date}</td>
      <td>${q.raisedBy}</td>
      <td>${q.title}</td>
      <td>${q.description}</td>
      <td>${q.assignedTo}</td>
      <td>${q.status}</td>
      <td>${q.response}</td>
      <td>${q.responseDate}</td>
      <td>${q.notes}</td>
      <td>${q.attachment === "—" ? "—" : `<a href="#">${q.attachment}</a>`}</td>
    `
    tbody.appendChild(tr)
  })
}

// Set Welcome User and initialize
window.addEventListener("DOMContentLoaded", async () => {
  console.log("=== Verified Page Loaded ===")

  // Load vendor data first
  await loadVendorData()

  // Set welcome message using real data
  const vendorName = projectData["Order-101"].dashboard["Name"] || "Vendor"
  document.getElementById("welcomeUser").textContent = "Welcome : " + vendorName

  // Set initial project selection and fill data
  const projectSelect = document.getElementById("projectSelect")
  let selectedProject = projectSelect.value
  displayProjectData(selectedProject)

  projectSelect.addEventListener("change", function () {
    selectedProject = this.value
    displayProjectData(selectedProject)
  })
})

// Fill project-based content in all relevant sections
function displayProjectData(projectKey) {
  const data = projectData[projectKey]
  if (!data) return

  // Dashboard - now shows real data from database
  fillDetailsGrid("dashboardGrid", data.dashboard)
  // Company Profile - using sample data (can be extended to load from DB)
  fillDetailsGrid("companyProfileGrid", data.companyProfile)
  // Project Info - using sample data (can be extended to load from DB)
  fillDetailsGrid("projectInfoGrid", data.projectInfo)
  // Submission History
  fillSubmissionHistoryTable(data.submissions)
  // Queries & Responses
  fillQueriesResponsesTable(data.queries)
}

// File upload alert
document.getElementById("documentForm").addEventListener("submit", (e) => {
  e.preventDefault()
  alert("File uploaded successfully!")
})

// Navbar toggling logic (keeping all your existing functionality)
const dashboardNav = document.getElementById("dashboardNav")
const companyProfileNav = document.getElementById("companyProfileNav")
const projectInfoNav = document.getElementById("projectInfoNav")
const docUploadNav = document.getElementById("docUploadNav")
const submissionHistoryNav = document.getElementById("submissionHistoryNav")
const queriesResponsesNav = document.getElementById("queriesResponsesNav")

const dashboardDetails = document.getElementById("dashboardDetails")
const companyProfileSection = document.getElementById("companyProfileSection")
const projectInfoSection = document.getElementById("projectInfoSection")
const docUploadSection = document.getElementById("docUploadSection")
const submissionHistorySection = document.getElementById("submissionHistorySection")
const queriesResponsesSection = document.getElementById("queriesResponsesSection")

function resetActiveNav() {
  dashboardNav.classList.remove("active")
  companyProfileNav.classList.remove("active")
  projectInfoNav.classList.remove("active")
  docUploadNav.classList.remove("active")
  submissionHistoryNav.classList.remove("active")
  queriesResponsesNav.classList.remove("active")
}

function hideAllSections() {
  dashboardDetails.style.display = "none"
  companyProfileSection.style.display = "none"
  projectInfoSection.style.display = "none"
  docUploadSection.style.display = "none"
  submissionHistorySection.style.display = "none"
  queriesResponsesSection.style.display = "none"
}

dashboardNav.addEventListener("click", (e) => {
  e.preventDefault()
  resetActiveNav()
  dashboardNav.classList.add("active")
  hideAllSections()
  dashboardDetails.style.display = "block"
})

companyProfileNav.addEventListener("click", (e) => {
  e.preventDefault()
  resetActiveNav()
  companyProfileNav.classList.add("active")
  hideAllSections()
  companyProfileSection.style.display = "block"
})

projectInfoNav.addEventListener("click", (e) => {
  e.preventDefault()
  resetActiveNav()
  projectInfoNav.classList.add("active")
  hideAllSections()
  projectInfoSection.style.display = "block"
})

docUploadNav.addEventListener("click", (e) => {
  e.preventDefault()
  resetActiveNav()
  docUploadNav.classList.add("active")
  hideAllSections()
  docUploadSection.style.display = "block"
})

submissionHistoryNav.addEventListener("click", (e) => {
  e.preventDefault()
  resetActiveNav()
  submissionHistoryNav.classList.add("active")
  hideAllSections()
  submissionHistorySection.style.display = "block"
})

queriesResponsesNav.addEventListener("click", (e) => {
  e.preventDefault()
  resetActiveNav()
  queriesResponsesNav.classList.add("active")
  hideAllSections()
  queriesResponsesSection.style.display = "block"
})

// Mapping from job number to Product Orders (keeping your existing functionality)
const jobProductOrders = {
  "Order-101": ["PO-101-A", "PO-101-B", "PO-101-C"],
  "Order-102": ["PO-102-X", "PO-102-Y"],
  "Order-103": ["PO-103-K"],
}

const jobNumberSelect = document.getElementById("jobNumber")
const productOrderRow = document.getElementById("productOrderRow")
const productOrderSelect = document.getElementById("productOrder")
const restOfDocFields = document.getElementById("restOfDocFields")

jobNumberSelect.addEventListener("change", function () {
  productOrderSelect.innerHTML = '<option value="">Select Product Order</option>'
  restOfDocFields.style.display = "none"
  if (this.value && jobProductOrders[this.value]) {
    // Populate Product Order dropdown
    jobProductOrders[this.value].forEach((po) => {
      const opt = document.createElement("option")
      opt.value = po
      opt.textContent = po
      productOrderSelect.appendChild(opt)
    })
    productOrderRow.style.display = "flex"
  } else {
    productOrderRow.style.display = "none"
  }
})

productOrderSelect.addEventListener("change", function () {
  if (this.value) {
    restOfDocFields.style.display = "block"
  } else {
    restOfDocFields.style.display = "none"
  }
})
