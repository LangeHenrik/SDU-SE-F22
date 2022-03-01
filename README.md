# SDU-SE-F22 - Hesehus Case

## Folder Structure

Each group should conform to the following folder structure, in order to minimize the amount of future merge errors. This folder structure should be used for the main java directory, as well as the test directory and the resources directory to ensure proper separation of modules.

The structure includes a main folder for each module, a folder for each submodule within the main folder, and a SharedLibrary folder for database connections etc.

```mermaid
flowchart LR
dk --- sdu --- javase[se_f22]

javase --- som[sortingmodule]
som --- som_som[category]
som --- som_inf[infrastructure]
som --- range
som --- scoring

javase --- sem[searchmodule]
sem --- sem_inf[infrastructure]
sem --- misspellings
sem --- onewaysynonyms
sem --- twowaysynonyms

javase --- sharedlibrary
SharedLibrary --- db
SharedLibrary --- models

javase --- cms[contentmodule]
cms --- cms_ind[index]
cms --- cms_man[management]
cms --- cms_inf[infrastructure]
cms --- stopwords

javase --- bim[brandmodule]
bim --- bim_ind[index]
bim --- bim_inf[infrastructure]
bim --- bim_man[management]
bim --- stemming

javase --- prm[productmodule]
prm --- prm_ind[index]
prm --- irregularwords
prm --- prm_inf[infrastructure]
prm --- prm_man[management]
```
