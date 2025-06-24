// --- Section Navigation Logic ---
function showSection(sectionId, navId) {
  // Sections
  document.querySelectorAll('.section').forEach(sec => sec.classList.remove('active-section'));
  document.getElementById(sectionId).classList.add('active-section');
  // Navbar
  document.querySelectorAll('.navbar a').forEach(a => a.classList.remove('active'));
  if (navId) document.getElementById(navId).classList.add('active');
}

// Nav click handlers
document.getElementById('nav-client-info').onclick = function(e) {
  e.preventDefault();
  showSection('section-client-info', 'nav-client-info');
};
document.getElementById('nav-company-info').onclick = function(e) {
  e.preventDefault();
  showSection('section-company-info', 'nav-company-info');
};
document.getElementById('nav-documents').onclick = function(e) {
  e.preventDefault();
  showSection('section-documents', 'nav-documents');
};
document.getElementById('nav-project').onclick = function(e) {
  e.preventDefault();
  showSection('section-project', 'nav-project');
};
document.getElementById('nav-ongoing-projects').onclick = function(e) {
  e.preventDefault();
  showSection('section-ongoing-projects', 'nav-ongoing-projects');
  // Reset selection to first (blank) project, hide table
  const sel = document.getElementById('ongoingProjectSelect');
  if (sel) {
    sel.selectedIndex = 0;
    sel.dispatchEvent(new Event('change'));
  }
};

// Next/Prev Button Logic
document.getElementById('toCompanyInfo').onclick = function() {
  if (document.getElementById('clientInfoForm').reportValidity()) {
    showSection('section-company-info', 'nav-company-info');
  }
};
document.getElementById('backToClientInfo').onclick = function() {
  showSection('section-client-info', 'nav-client-info');
};
document.getElementById('toDocuments').onclick = function() {
  if (document.getElementById('companyInfoForm').reportValidity()) {
    showSection('section-documents', 'nav-documents');
  }
};
document.getElementById('backToCompanyInfo').onclick = function() {
  showSection('section-company-info', 'nav-company-info');
};
document.getElementById('toProject').onclick = function() {
  if (document.getElementById('documentsForm').reportValidity()) {
    showSection('section-project', 'nav-project');
  }
};
document.getElementById('backToDocuments').onclick = function() {
  showSection('section-documents', 'nav-documents');
};

// --- Show/Hide MSME & GST document rows ---
document.getElementById('msme').onchange = function() {
  if (this.value === 'yes') {
    document.getElementById('udyamRow').style.display = '';
    document.getElementById('udyam').setAttribute('required', 'required');
    document.getElementById('udyamDocRow').style.display = '';
  } else {
    document.getElementById('udyamRow').style.display = 'none';
    document.getElementById('udyam').removeAttribute('required');
    document.getElementById('udyamDocRow').style.display = 'none';
  }
};

document.getElementById('gstin').oninput = function() {
  if (this.value.trim().length > 0) {
    document.getElementById('gstDocRow').style.display = '';
    document.getElementById('gstDoc').setAttribute('required', 'required');
  } else {
    document.getElementById('gstDocRow').style.display = 'none';
    document.getElementById('gstDoc').removeAttribute('required');
  }
};

// --- Project Tabs Logic ---
const requestTab = document.getElementById("project-request-tab");
const matchesTab = document.getElementById("project-matches-tab");
const requestContent = document.getElementById("project-request");
const matchesContent = document.getElementById("project-matches");

requestTab.onclick = function() {
  requestTab.classList.add('active');
  matchesTab.classList.remove('active');
  requestContent.classList.add('active');
  matchesContent.classList.remove('active');
};
matchesTab.onclick = function() {
  matchesTab.classList.add('active');
  requestTab.classList.remove('active');
  matchesContent.classList.add('active');
  requestContent.classList.remove('active');
};

// --- Service Request Submission ---
document.getElementById('serviceRequestForm').onsubmit = function(e) {
  e.preventDefault();
  const status = document.getElementById("serviceRequestStatus");
  if (!this.reportValidity()) return;
  status.textContent = "Project request submitted!";
  status.style.display = "block";
  status.style.color = "#16a34a";
  setTimeout(() => { status.style.display = "none"; }, 2000);
  this.reset();
};

// --- Document Upload (simulate) ---
document.getElementById('documentsForm').onsubmit = function(e) {
  e.preventDefault();
  alert('Documents uploaded (simulation).');
  this.reset();
};

// --- Populate Matches (fake) ---
const matchedVendors = [
  { name: "Vendor A", expertise: "Mechanical Inspection", contact: "vendora@vendors.com" },
  { name: "Vendor B", expertise: "Electrical QA", contact: "vendorb@vendors.com" },
  { name: "Vendor C", expertise: "Civil Engineering", contact: "vendorc@vendors.com" }
];
const matchesList = document.getElementById("matchedVendorsList");
matchesList.innerHTML = matchedVendors.length === 0
  ? "<li>No matches yet.</li>"
  : matchedVendors.map(v =>
      `<li style="margin-bottom:9px;"><strong>${v.name}</strong> <br/><span style="color:#002e5d">${v.expertise}</span><br/>
      <a href="mailto:${v.contact}">${v.contact}</a></li>`
    ).join('');

// ---- USER PROJECTS DATA ---- //
const userProjects = [
  {id:1, code:"Order-101", name:"Metro Project"},
  {id:2, code:"Order-102", name:"Bridge Project"},
  {id:3, code:"Order-103", name:"IT Infra"}
];

const projectSubmissions = {
  1: [
    {
      type: "Invoice",
      filename: "invoice_april2025.pdf",
      filelink: "/docs/invoice_april2025.pdf",
      submittedBy: "Priya Sharma",
      date: "2025-06-10",
      status: "Pending",
      notes: "Will review soon"
    },
    {
      type: "Contract",
      filename: "final_contract.pdf",
      filelink: "/docs/final_contract.pdf",
      submittedBy: "Priya Sharma",
      date: "2025-06-09",
      status: "Approved",
      notes: "—"
    }
  ],
  2: [
    {
      type: "Document Upload",
      filename: "bridge_design.pdf",
      filelink: "#",
      submittedBy: "Anjali Singh",
      date: "2025-06-15",
      status: "Pending",
      notes: "Awaiting review"
    },
    {
      type: "Contract",
      filename: "updated_contract.pdf",
      filelink: "/docs/final_contract.pdf",
      submittedBy: "Anjali Sharma",
      date: "2025-06-10",
      status: "Rejected",
      notes: "—"
    }
  ],
  3: []
};

const ongoingProjectSelect = document.getElementById('ongoingProjectSelect');
const ceilTableDynamicContainer = document.getElementById('ceil-table-dynamic-container');

// Populate project dropdown, keep blank first option
ongoingProjectSelect.innerHTML = '<option value="">Select Project</option>';
userProjects.forEach(proj => {
  const opt = document.createElement('option');
  opt.value = proj.id;
  opt.textContent = `${proj.code} – ${proj.name}`;
  ongoingProjectSelect.appendChild(opt);
});

// Hide table if no selection, show and fill if project selected
ongoingProjectSelect.addEventListener('change', function () {
  if (!this.value) {
    ceilTableDynamicContainer.style.display = 'none';
    ceilTableDynamicContainer.innerHTML = '';
    return;
  }
  // Render dynamic table for selected project
  const projectId = this.value;
  const docs = projectSubmissions[projectId] || [];
  let tableRows = '';
  if (docs.length === 0) {
    tableRows = `<tr><td colspan="8" style="text-align:center;">No documents to show.</td></tr>`;
  } else {
    docs.forEach((row, idx) => {
      tableRows += `
        <tr>
          <td>${idx + 1}</td>
          <td>${row.type}</td>
          <td>${row.filename}</td>
          <td>${row.filelink ? `<a href="${row.filelink}" target="_blank">View</a>` : ""}</td>
          <td>${row.submittedBy}</td>
          <td>${row.date}</td>
          <td>${row.status}</td>
          <td>${row.notes}</td>
        </tr>
      `;
    });
  }

  ceilTableDynamicContainer.innerHTML = `
    <div class="ceil-table-container">
      <div class="ceil-table-title">Submission History</div>
      <table class="ceil-table">
        <thead>
          <tr>
            <th>S. No.</th>
            <th>Type</th>
            <th>File Name</th>
            <th>Download/View</th>
            <th>Submitted By</th>
            <th>Date</th>
            <th>Status</th>
            <th>Notes</th>
          </tr>
        </thead>
        <tbody>
          ${tableRows}
        </tbody>
      </table>
    </div>
  `;
  ceilTableDynamicContainer.style.display = '';
});
// --- Support Modal Logic ---
const openSupportModal = document.getElementById('openSupportModal');
const closeSupportModal = document.getElementById('closeSupportModal');
const supportModal = document.getElementById('supportModal');

openSupportModal.onclick = function() {
  supportModal.style.display = 'flex';
}
closeSupportModal.onclick = function() {
  supportModal.style.display = 'none';
}
window.onclick = function(event) {
  if (event.target === supportModal) {
    supportModal.style.display = 'none';
  }
}

// --- Support Form Submission (simulate auto-email) ---
document.getElementById('supportForm').onsubmit = function(e) {
  e.preventDefault();
  // Here you would typically send the data to your server/email service via AJAX/fetch.
  // Simulate auto-email send and success message:
  const status = document.getElementById('supportFormStatus');
  status.textContent = "Your query has been submitted. Our support team will contact you soon!";
  status.style.display = "block";
  setTimeout(() => {
    status.style.display = "none";
    supportModal.style.display = "none";
    this.reset();
  }, 2500);
};