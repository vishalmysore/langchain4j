# Pure java Implementation of CookGPT
![CookGPT - Pure Java](python_java.png "Java, LocalAI and LangChain4j using Spring boot")

## Setup

Setup localai - 
`docker run -ti -p 8080:8080 localai/localai:v2.8.0-ffmpeg-core phi-2`
<br>
setup chromadb
`docker pull chromadb/chroma`
`docker run -p 8000:8000 chromadb/chroma`
<br>
add the details for chromadb and localai server in application.yaml

## Instructions

mvn clean install <br>
mvn spring-boot:run <br>
starter html page http://localhost:8080/cookgpt.html <br>
Swagger - http://localhost:8080/swagger-ui.html <br>
