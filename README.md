
# CA Exercise 2 – Elections Information System

## Overview
This project involves creating an application to store and retrieve information on Irish political elections. The system must implement **hashing** and **sorting** algorithms from scratch and provide a **JavaFX graphical user interface**.

---

## Features
- **Add Politician**
    - Name, Date of Birth
    - Political Party (or Independent)
    - Home County (e.g., Waterford)
    - Image/Photo (URL)
- **Add Election**
    - Types: General, Local, European, Presidential
    - Location (e.g., county for locals)
    - Year/Date
    - Number of Winners/Seats
- **Add Candidate**
    - Link politician to election
    - Record total votes
    - Party affiliation at election level
- **Edit/Update/Delete**
    - Politicians, Elections, Candidates
- **Search & Filter**
    - Elections by type/year
    - Politicians by name, party, location
- **Sorting**
    - At least two sorting options (e.g., name, party, year)
- **View Details**
    - Politician: name, photo, party, election history
    - Election: type, year, candidates sorted by votes (highlight winners)
- **Interactive Drill Down**
    - Navigate between politicians and elections
- **Persistence**
    - Save data to file on exit and reload on start (binary, XML, or text)

---

## Technical Requirements
- **Custom Data Structures**
    - No Java Collections (ArrayList, LinkedList, etc.)
    - Implement from first principles
- **Sorting**
    - No built-in Java sorting methods
    - No bubble sort (use alternative algorithm)
- **Hashing**
    - Custom hash function for efficient lookup
- **JavaFX GUI**
    - Command-line option allowed (but GUI marks lost)
- **Persistence**
    - Single snapshot file (no database)
- **JUnit Testing**
    - Minimum 6–8 useful unit tests

---

## Notes
- Team size: 2–3 students
- Demonstration via Zoom; individual interviews on code
- Worth **35%** of overall module mark

---

## Indicative Marking Scheme
| Component                                      | Marks |
|-----------------------------------------------|-------|
| Custom ADTs (politicians, elections, etc.)   | 10%   |
| Create/Add facilities                        | 10%   |
| Edit/Update/Delete facilities                | 10%   |
| Search & Listing facilities                  | 15%   |
| Sorting of results                           | 10%   |
| Hashing for lookup                           | 10%   |
| Persistence facility                         | 10%   |
| JavaFX GUI (with drill down)                 | 10%   |
| JUnit Testing                                | 5%    |
| General (style, commenting, completeness)    | 10%   |

---
