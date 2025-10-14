# USE CASE: Generate Language Demographics Report

## CHARACTERISTIC INFORMATION

### Goal in Context

*As a Data Analyst, I want to know the number of people who speak Chinese, English, Hindi, Spanish 
and Arabic globally, including the percentage of the world population so that I can report on language demographics.*

### Scope

*Global population and language reporting system.*

### Level

*Primary task*

### Preconditions

*The database contains accurate and up-to-date data on global population and the number of speakers for each specified language.
The user has authorized access to generate global demographic reports.*

### Success End Condition

*The system successfully generates and displays a language demographics report showing each of the five specified languages, the number of speakers and the percentage of the world population.*

### Failed End Condition

*No report is produced due to missing or invalid data.*

### Primary Actor

*Data Analyst*

### Trigger

*A request for a global language demographics report is initiated by the Data Analyst.*

### MAIN SUCCESS SCENARIO

*1. Data Analyst initiates a request for the language demographics report.*
*2. System retrieves the total world population data.*
*3. System retrieves the number of speakers for Chinese, English, Hindi, Spanish, and Arabic.*
*4. System calculates the percentage of the world population for each language.*
*5. System generates and displays the report with columns: Language, Number of Speakers, Percentage of World Population.*
*6. Data Analyst reviews or exports the report for further analysis.*

## EXTENSIONS

*If population or language data is unavailable or outdated: System notifies the user that the report cannot be generated and prompts to refresh or update the dataset.*

*If calculation errors occur: System displays a message indicating failure to compute percentages accurately.*

## SUB-VARIATIONS

None

## SCHEDULE

**DUE DATE**: *Release 1.0*