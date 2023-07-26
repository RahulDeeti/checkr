### added users controller to create users  
/api/users  
### Added candidates controller  
create candidate /api/candidates/1  
1. get all candidates /api/candidates  
2. get candidates /api/candidates/{id}  
3. filter candidates by name, status, adjudication, any combination is fine, if no query param is provided it will fetch all rows
i.e /api/candidates?name=value1&status=somestatus&adjudication=something