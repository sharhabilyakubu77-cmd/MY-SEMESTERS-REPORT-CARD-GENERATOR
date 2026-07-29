# Student Grade Evaluation & Report Card System 🎓

A robust Java Swing desktop application designed to evaluate student exam scores, compute cumulative academic averages, and generate formal academic report cards with built-in export capabilities. Tailored with specific engineering and general university courses for Ghana Communication Technology University (GCTU).

---

## 🚀 Features

* **Multi-Course Input Interface:** Clean, scrollable form layout to input scores for a wide range of academic subjects.
* **Automated Grading Logic:** Automatically assigns letter grades ($\text{A}$ to $\text{F}$) and descriptive remarks based on standard grading thresholds:
  * $\ge 70\%$ $\rightarrow$ **A** (Pass - Excellent)
  * $\ge 60\%$ $\rightarrow$ **B** (Pass - Very Good)
  * $\ge 50\%$ $\rightarrow$ **C** (Pass - Credit)
  * $\ge 45\%$ $\rightarrow$ **D** (Pass - Pass)
  * $< 45\%$ $\rightarrow$ **F** (Fail)
* **Cumulative Performance Tracking:** Computes the cumulative average score and determines the overall academic status (Pass/Fail).
* **Official Report Card Layout:** Generates a structured, monospaced institutional report card displaying student details, individual course scores, grades, and remarks.
* **Export & Print Options:** Built-in support to print or export the official report card directly to a **PDF** or text file via native OS dialogs.

---

## 📚 Included Courses

The application evaluates performance across the following curriculum:
* Critical Thinking and Logical Reasoning
* Circuit Theory
* Technical Communication Skills
* Cyber Law
* Engineering Drawings
* Basic Electronics (Semiconductors)
* Engineering Mathematics
* Programming in C++ (Arduino)

---

## 💻 Prerequisites

* **Java Development Kit (JDK 8 or higher)** installed on your machine.
* An Integrated Development Environment (IDE) such as **IntelliJ IDEA**, Eclipse, or VS Code.

---

## 🛠️ Getting Started & Installation

1. **Clone or Download** this repository to your local machine.
2. Open your preferred Java IDE (**IntelliJ IDEA** recommended).
3. Create a new Java project and place the `StudentGradeEvaluator.java` file inside your `src` directory.
4. Run the application:
   * **In IntelliJ IDEA:** Open the file and click the green play button ($\blacktriangleright$) next to the `main` method.
   * **Via Terminal:**
     ```bash
     javac StudentGradeEvaluator.java
     java StudentGradeEvaluator
     ```

---

## 📝 Usage Guide

1. Launch the application to open the **Student Report Card & Grade Evaluation System** window.
2. Enter the student's full name in the top input field.
3. Input numeric exam scores (ranging between `0` and `100`) for each listed course.
4. Click **Generate Report Card** to process the scores and render the formal record in the text box below.
5. Click **Export / Print as PDF** to invoke your system's print dialog, choose **Save as PDF**, and save your official academic slip.
