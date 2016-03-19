package utils;

import fakeclasses.FakeAccountService;
import main.UserProfile;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

/**
 * Created by Installed on 18.03.2016.
 */
public class TestGenerator {

    public static final String FILE_PATH_OF_NOT_AUTHENTICATED = "testData/generatedNotAuthenticatedUsers.json";
    public static final String FILE_PATH_OF_AUTHENTICATED = "testData/generatedAuthenticatedUsers.json";
    public static final Long FIRST_ID_OF_NOT_AUTHENTICATED = 0L;
    public static final Long LAST_ID_OF_NOT_AUTHENTICATED = 5L;
    public static final Long FIRST_ID_OF_AUTHENTICATED = 6L;
    public static final Long LAST_ID_OF_AUTHENTICATED = 10L;

    public static void main(String[] args) throws InterruptedException {
        generateUsers(FIRST_ID_OF_NOT_AUTHENTICATED, LAST_ID_OF_NOT_AUTHENTICATED, false, FILE_PATH_OF_NOT_AUTHENTICATED);
        generateUsers(FIRST_ID_OF_AUTHENTICATED, LAST_ID_OF_AUTHENTICATED, true, FILE_PATH_OF_AUTHENTICATED);
    }

    public static FakeAccountService generateFakeAccountService() {
        FakeAccountService fakeAccountService = new FakeAccountService();
        parseAccountServiceFromJSONFile(fakeAccountService, FILE_PATH_OF_NOT_AUTHENTICATED, false);
        parseAccountServiceFromJSONFile(fakeAccountService, FILE_PATH_OF_AUTHENTICATED, true);
        return fakeAccountService;
    }

    public static boolean generateUsers(Long startId, Long endId,
                                                     boolean areAuthenticated, String filePath) {
        JSONStringer jsonStringer = new JSONStringer();
        jsonStringer.array();
        for (Long id = startId; id < endId; ++id) {
            jsonStringer.
                    object()
                        .key("id").value(id)
                        .key("login").value("login" + id.toString())
                        .key("password").value("password" + id.toString())
                        .key("email").value("email" + id.toString() + "@email.com");
                        if(areAuthenticated) { jsonStringer.key("session").value("session" + id.toString()); }
                    jsonStringer.endObject();
        }
        jsonStringer.endArray();
        return writeJSONToFile(filePath, jsonStringer.toString());
    }

    public static void parseAccountServiceFromJSONFile(FakeAccountService fakeAccountService,
                                                       String filePath, boolean areAuthenticated) {
        String strUsers = readJSONFromFile(filePath);
        JSONArray arrWithUsers = new JSONArray(strUsers);
        Iterator<Object> it = arrWithUsers.iterator();
        while(it.hasNext()) {
            JSONObject userJSON = (JSONObject) it.next();
            Number id = (Number) userJSON.get("id");
            String login = (String)userJSON.get("login");
            String password = (String)userJSON.get("password");
            String email = (String)userJSON.get("email");
            UserProfile userProfile = new UserProfile(id.longValue(), login, password, email);
            fakeAccountService.addUser(userProfile);
            if(areAuthenticated) {
                String session = (String)userJSON.get("session");
                fakeAccountService.addSession(session, userProfile);
            }
        }
    }

    public static boolean writeJSONToFile(String filePath, String text) {
        File file = new File(filePath);
        try {

            if (!file.exists()) {
                file.createNewFile();
            }

            PrintWriter printWriter = new PrintWriter(file.getAbsoluteFile());
            printWriter.print(text);
            printWriter.close();
            return true;
        }
        catch (IOException e) {
            System.out.append(e.getMessage() + "\n");
            return false;
        }
    }

    public static String readJSONFromFile(String filePath) {
        File file = new File(filePath);
        try {

            if (!file.exists()) {
                return null;
            }

            char[] buffer = new char[(int)file.length()];
            FileReader fileReader = new FileReader(file);
            fileReader.read(buffer);
            return new String(buffer);
        }
        catch (IOException e) {
            System.out.append(e.getMessage() + "\n");
            return null;
        }
    }


}
