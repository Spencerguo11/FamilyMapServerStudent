package Service;


import Model.Authtoken;
import Model.Person;
import Model.User;
import Result.PersonIDResult;
import dao.*;

public class PersonIDService {

    /**
     * current user determined by authtoken
     * @return person object with specified ID
     */
    private Database db;

    public PersonIDService() {
        db = new Database();
    }

    public PersonIDResult personID(String personID, String authtoken){
        PersonIDResult idResult = new PersonIDResult();

        try{
            db.openConnection();
            PersonDAO pDao = db.getPersonDAO();
            AuthtokenDAO aDao = db.getAuthtokenDAO();
            UserDAO userDAO = db.getUserDAO();


            if(aDao.validAuthToken(authtoken)){
                Authtoken auth = aDao.getAuthToken(authtoken);
                User founduser = userDAO.getUser(auth.getUsername());
                if (!personID.equals(founduser.getPersonID())){
                    throw new DataAccessException("{\"message\" : \" internal server error\"}");
                }
                if (pDao.find(personID)){
                    Person out = pDao.selectSinglePerson(personID);
                    idResult = new PersonIDResult(out);
                }

            }

            idResult.setSuccess(true);
            db.closeConnection(true);
            idResult.setSuccess(true);

        } catch (DataAccessException e){
            idResult.setSuccess(false);
            idResult.setMessage(e.getMessage());
            try{
                db.closeConnection(false);
            }catch (DataAccessException d){
                idResult.setSuccess(false);
                idResult.setMessage(d.getMessage());
            }
        }
        return idResult;
    }
}