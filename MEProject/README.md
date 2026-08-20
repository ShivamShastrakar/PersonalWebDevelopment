# MahaExam
MahaExam

# Playwright E2E to test MahaExam application

This project contains an end-to-end (E2E) test automation framework using **Playwright**, written in **TypeScript** and structured with the **Page Object Model (POM)**.  
All automation-related files are located under the `e2eAutomation/` directory.

---

## 📁 Folder Structure

```bash
e2eAutomation/
├── tests/ # Test specs
├── pages/ # Page Object classes
├── utils/ # Utilities (env, logger, helpers, etc.)
│ ├── env.ts
│ ├── helpers.ts # Helper functions to generate random mobile no., email ids & get current date
│ ├── logger.ts
│ └── waiters.ts # Waiting for selector function
├── .env # Environment variables
├── .gitignore
├── package.json
├── playwright.config.ts
├── tsconfig.json
```

---

## ⚙️ Prerequisites

- Node.js v18 or higher
- npm v9 or higher
- Git installed

---

## 🚀 Getting Started

1. **Navigate to the project directory**:

```bash
    cd e2eAutomation
```

2. **Install dependencies**:
```bash
    npm install
```

3. **Install Playwright browsers**:
```bash
    npx playwright install
```

---

## 🌐 Environment Variables Setup
Create a `.env` file inside the `e2eAutomation/` directory:

```bash
BASE_URL=http://3.110.16.23/MahaExam
USERNAME=admin
PASSWORD=password
```

## 🧪 Running Tests

### Run all tests
```bash
npm run test
```

### Run in UI mode
```bash
npm run test-ui-mode
```

### Run a specific test file
```bash
npx playwright test tests/registerStudent.spec.ts
```

## 📊 Reporting

### ▶ Allure Report
```bash
npm run report
```
Open the report at `e2eAutomation/allure-report/index.html`
