# SIT305 Quiz App

The **SIT305 Quiz App** is a fully functional Android quiz app currently providing one quiz pack: questions relating to the cult classic 2002 PlayStation 2 game 'Haven: Call of the King'. This 5-question quiz showcases real-time progress tracking and instant visual feedback on answers, as well as demonstrating multi-activity navigation and data persistence.

## Features
### Answer Selection & Visual Feedback
- Green highlighting for correct answers
- Red highlighting for incorrect selected answers
- Simultaneous green highlighting of correct answer when user is incorrect
- Disabled buttons prevent answer changes after submitting
- Dynamic button text changes from "SUBMIT" to "NEXT" after submission

### Real-Time Progress Tracking
- Horizontal progress bar at the top of quiz screen
- Dynamic percentage calculation based on current question
- "X/5" progress test showing the current question
- Real-time updates as user progresses through quiz

### Final Score & Session Persistence
- Results activity displaying the player's final score
- "Take New Quiz" button returns to the start screen with name retained
- Player name persisted in SharedPreferences storage
- "Finish" button closes the entire application
