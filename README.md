# EduConnect - Android App 📱🎓

**Training and Placement Hub** — A comprehensive Android application developed to support college students in preparing for placements with organized materials, ATS resume scoring, and static resume templates.

---

## 🚀 Key Features

### 👨‍🎓 For Students:
- **📚 Semester-Wise Study Materials**  
  Students can access learning content based on their current and previous semesters, filtered using their college registration number.

- **📄 ATS Resume Scoring System**  
  Students can upload their chosen static resume template and receive an **ATS (Applicant Tracking System) score** using a machine learning model based on BERT. This helps assess how well the resume matches a given job title and description.

- **📑 Resume Template Library**  
  Access a wide variety of ATS-friendly, static resume templates optimized for different job roles and domains.

---

## 🧑‍💼 For Admins:
- Upload semester-wise study materials
- Manage student and app content access
---

## 🔐 Access Control

- **Login Format**: College registration number  
  Format → `YYCCXBDDNN` (e.g., `21331A05B0`)  
  - `YY` → Year of joining  
  - `CC` → College code  
  - `X` → Entry Type (`1A` for regular, `5A` for lateral)  
  - `BD` → Branch code  
  - `NN` → Roll number

- Students can access only their semester’s content and previous semesters.

- Admin users can upload and manage study materials for all branches and semesters.

---

## 🛠️ Tech Stack

- **Frontend & App Logic**: Kotlin (Android Studio)
- **UI Design**: XML Layout Files
- **Database**: Firebase Realtime Database
- **Authentication**: Firebase Authentication
- **Cloud Storage**: Firebase Cloud Storage
- **ML Integration**:  
  - Model built using Python and BERT in Jupyter Notebook  
  - Integrated via **ngrok** for local API tunneling

---

## 🤖 About the ATS Model

- The **ATS Scoring System** uses a pretrained **BERT model** to evaluate resume relevance against specific job titles/descriptions.
- The scoring system runs via a **local Flask server** tunneled to the app using **ngrok**.

---

## 📷 Screenshots

<h3>App Screenshots</h3>

<div style="display: flex; gap: 20px; margin-bottom: 20px;">
  <img src="https://github.com/user-attachments/assets/cdf5573e-eac9-4934-be61-112a9312a10e" width="45%">
  <img src="https://github.com/user-attachments/assets/882a4815-7994-49d2-8765-549530810ce1" width="45%">
</div>

<div style="display: flex; gap: 20px;">
  <img src="https://github.com/user-attachments/assets/4facbcf0-9ddd-4623-83c8-4e46007a034b" width="45%">
  <img src="https://github.com/user-attachments/assets/a3c70b6b-eb0c-4b84-acf0-450c8161c802" width="45%">
</div>


---

## 🧠 Developed By

**Team EduConnect**  
Final Year B.Tech CSE Students, MVGR College Of Engineering | 2025  
Built with a mission to simplify student placement preparation using mobile technology and machine learning.

---

## 📂 License

This project is developed for academic and demonstration purposes.
