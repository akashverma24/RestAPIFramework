package Utility;

import java.io.*;

public class JsonDataReader {


    public String getFileData(String filePath) throws IOException {
        BufferedReader bufferedReader = null;
        StringBuffer  stringBuffer = new StringBuffer();
        try{

            bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
            String currentLine = null;
            while((currentLine = bufferedReader.readLine())!=null){
                stringBuffer.append(currentLine+"\n");

            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(bufferedReader!=null){
                try{
                    bufferedReader.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return stringBuffer.toString();


    }




}
