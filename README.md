# SDU-SE-F22 - Hesehus Case

## Folder Structure

Each group should conform to the following folder structure, in order to minimize the amount of future merge errors. This folder structure should be used for the main java directory, as well as the test directory and the resources directory to ensure proper separation of modules.

The structure includes a main folder for each module, a folder for each submodule within the main folder, and a SharedLibrary folder for database connections etc.

```mermaid
flowchart LR
dk --- sdu --- javase[se_f22]

javase --- som[Sorting Module]
som --- som_som[Category]
som --- som_inf[Infrastructure]
som --- Range
som --- Scoring

javase --- sem[Search Module]
sem --- sem_inf[Infrastructure]
sem --- Misspellings
sem --- OneWaySynonyms
sem --- TwoWaySynonyms

javase --- SharedLibrary
SharedLibrary --- db
SharedLibrary --- models

javase --- cms[Content Module]
cms --- cms_ind[Index]
cms --- cms_man[Management]
cms --- cms_inf[Infrastructure]
cms --- Stopwords

javase --- bim[Brand Indexing]
bim --- bim_ind[Index]
bim --- bim_inf[Infrastructure]
bim --- bim_man[Management]
bim --- Stemming

javase --- prm[Product Module]
prm --- prm_ind[Index]
prm --- IrregularWords
prm --- prm_inf[Infrastructure]
prm --- prm_man[Management]
```
