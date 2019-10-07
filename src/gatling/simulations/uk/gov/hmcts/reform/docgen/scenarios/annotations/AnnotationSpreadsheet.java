package uk.gov.hmcts.reform.docgen.scenarios.annotations;

import java.io.*;
import java.util.UUID;
import java.util.Random;

public class AnnotationSpreadsheet{

    public static void main(String[] args){
        try{
            FileWriter fw = new FileWriter("C:\\Vijay\\feeder_05_october8.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            for(int i = 0; i < 2000; i++){
                pw.println(UUID.randomUUID());
                       /* +","
                        +UUID.randomUUID()
                        +","
                        +(int) Math.floor(Math.random() * 201 +1)
                        +","
                        +(Math.random() * 500)
                        +","
                        +(Math.random() * 300)
                        +","
                        +UUID.randomUUID()
                        +","
                        +UUID.randomUUID());*/
            }
            pw.flush();
            pw.close();
            System.out.println("Done saving");

        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
