# object-state-service
We aim to create the microservice of creating state control of targeted system requests.
We suggest to create the internal state model and persist that to SQL based database.
We suggest to create the special REST API which helps us to validate correct state transitions. All illegal transitions will be eliminated.
The validated request of changing object state will be persisted to the history table. The history table is avaliable through the special REST API. 
