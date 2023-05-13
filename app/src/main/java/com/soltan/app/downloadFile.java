package com.soltan.app;

//import com.google.api.client.googleapis.json.GoogleJsonResponseException;
//import com.google.api.client.http.HttpRequestInitializer;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.gson.GsonFactory;
//import com.google.api.services.drive.Drive;
//import com.google.api.services.drive.DriveScopes;


//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
//import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
/* Class to demonstrate use-case of drive's download file. */
class DownloadFile {

//    /**
//     * Download a Document file in PDF format.
//     * @param realFileId file ID of any workspace document format file.
//     * @return byte array stream if successful, {@code null} otherwise.
//     * @throws IOException if service account credentials file not found.
//     */
//    private static ByteArrayOutputStream downloadFile(String realFileId) throws IOException{
//        /* Load pre-authorized user credentials from the environment.
//           TODO(developer) - See https://developers.google.com/identity for
//          guides on implementing OAuth2 for your application.*/
//        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault().createScoped(Arrays.asList(DriveScopes.DRIVE_FILE));
//        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
//                credentials);
//
//        // Build a new authorized API client service.
//        Drive service = new Drive.Builder(new NetHttpTransport(),
//                GsonFactory.getDefaultInstance(),
//                requestInitializer)
//                .setApplicationName("Drive samples")
//                .build();
//
//        try {
//            OutputStream outputStream = new ByteArrayOutputStream();
//
//            service.files().get(realFileId)
//                    .executeMediaAndDownloadTo(outputStream);
//
//            return (ByteArrayOutputStream) outputStream;
//        }catch (GoogleJsonResponseException e) {
//            // TODO(developer) - handle error appropriately
//            System.err.println("Unable to move file: " + e.getDetails());
//            throw e;
//        }
//    }
}
