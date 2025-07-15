# 📱 Personal Finance Tracker App 

This is an Android-based personal finance tracker application that helps users manage their income and expenses with visual insights, personalized advice, and a modern UI/UX experience.

---

## 🚀 Features

### 1. 🔐 Login Functionality
- Secure user authentication using SQLite.
- Each user has a unique username and password.
- Separate data storage (table/section) in SQLite for each user’s income and expense data.
- User registration and session management (planned).

---

### 2. 🔙 Navigation (Back Button Support)
- Back button added to:
  - 📊 Bar Chart Page
  - ➕ Add Transaction Page
- Smooth navigation back to `MainActivity`.

---

### 3. ➕ Add Transaction Enhancements
- In the Expense category spinner:
  - Added subcategories under Utilities:
    - Electricity, Water, Gas, Society Charges, Internet, Mobile Recharge
- “Other” Category:
  - Users can input custom categories, amounts, and notes.

---

### 4. 📊 Graphs and Charts
- Bar and Line graphs to visualize:
  - Monthly Income
  - Monthly Expenses
  - Monthly Balance (Income - Expense)
- Button added in `MainActivity` to view graphs using MPAndroidChart.

“income vs expense statement chart” button 
When you click on that button you must get another button select year with a back button below it to transition back to previous page.
Then once you click the year button then give two more button "january to june statement chart” “june to july statement chart” and once you select a duration button you just  see a graph with the y axis with amounts in rupees like 2500,5000,75000,25,000,50000,75,000 upto 1.5 lakhs and on x axis all the 3 months of the selected duration in order  and then must show the following chart elements 
📊 Chart Elements:
🔴 Red Bars:
 These represent monthly expenses
Taller red bars indicate higher expenses in that particular month.
🔵 Blue Bars:
 These represent monthly income.
Taller blue bars indicate more income earned in that month.
🟢 Green Line:
 This represents the net balance (Income − Expense) for each month.
When the green line is higher, it means income exceeded expenses (positive balance).
When the green line dips, it indicates that expenses may have exceeded income (negative balance or lower surplus)

---

### 5. 🤖 Chatbot Integration (Advanced)
- Integrate ChatGPT API (optional future scope).
- Features:
  - Track financial behavior
  - Offer advice to control spending
  - Alert users on potential overspending
  - Suggest budget optimization strategies

---

### 6. 💰 Income vs 💸 Expense Filters
- Two buttons on `MainActivity`:
  - Income → List all income transactions
  - Expense → List all expense transactions
- Data filtered from SQLite and shown using RecyclerView.

---

### 7. 🎨 UI/UX Enhancements
- Clean, modern user interface using Material Design.
- Icons for:
  - Add Transaction
  - Income
  - Expense
  - Charts
  - Settings
- Improved layout with rounded buttons, shadows, and card views.

---

## 🛠️ Tech Stack

- Language: Java
- IDE: Android Studio
- Database: SQLite
- UI: XML + Material Design
- Charting: MPAndroidChart
- API (Planned): OpenAI ChatGPT (for chatbot)

---

## ✅ TODO List Summary

- [ ] Login & Registration
- [ ] Back button navigation
- [ ] Expanded expense categories
- [ ] Add "Other" category entry support
- [ ] Income vs Expense chart with monthly breakdown
- [ ] ChatGPT-powered financial assistant (planned)
- [ ] Income & Expense list filters
- [ ] Icon and design overhaul

---

## 📂 Folder Structure (Preview)

