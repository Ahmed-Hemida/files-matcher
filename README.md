##  Problem
* There is one reference text file (File A). - There is a directory containing 1–20 text files. - The application reads both paths from application.properties. - It compares every file in the directory against File A. - Each file receives a similarity score. - The application returns the files sorted by similarity, with the best match first.

## System High Level Design (HLD)
                +----------------+
                | application.yml|
                +--------+-------+
                         |
                         v
                 Config Loader
                         |
                         |
                 File Discovery
                         |
          +--------------+-------------+
          |                            |
          v                            v
 Parse File A                 Parse Pool Files
          |                            |
          +--------------+-------------+
                         |
                  Similarity Services
                         |
                  Parallel Processing
                         |
                 Score Calculator
                         |
                  Ranking Service
                         |
                    Console Output

### application.properties

* reference.file.path for "A File"
* pool.files.dir for path of the directory that contains the other files from its properties file


* reference.file.path is path for  "file A"
* pool.files.dir is path for directory that contains the other files from its properties file


### Dataset 
* [dataset: D670MB.zip ](https://zenodo.org/records/3360392)

