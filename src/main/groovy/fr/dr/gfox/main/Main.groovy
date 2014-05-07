package fr.dr.gfox.main

import fr.dr.gfox.parser.JsonParser
import fr.dr.gfox.parser.Sqlite
import java.sql.SQLException

/**
 * GFox.
 * Read all files from  mozilla directory and extract information
 * User: drieu
 * Date: 25/04/14
 */
class Main {

    public static void main(String[] args) {
        System.out.println("Working!");
        def MOZILLA_DIR = "/home/drieu/tmp/.mozilla/firefox/"

        new File(MOZILLA_DIR).eachDir {
            d ->
                println("Extract information from Directory :" + d.getPath())
                new File( d.getPath() ).eachFile() {
                    f->

                        Sqlite sqlite = new Sqlite(f);
                        if(f?.getName().startsWith("cookies.sqlite")) {
                            try {
                                sqlite.getCookies();
                            } catch(SQLException sqle) {
                                //
                            }
                        }

                        if(f?.getName().startsWith("addons.sqlite")) {
                            try {
                                sqlite.getAddons();
                            } catch(SQLException sqle) {
                                //
                            }
                        }

                        if(f?.getName().startsWith("formhistory.sqlite")) {
                            try {
                                sqlite.getHistory();
                            } catch(SQLException sqle) {
                                //
                            }
                        }
                        //content-prefs.sqlite
                        //healthreport.sqlite
                        //permissions.sqlite
                        //places.sqlite
                        //signons.sqlite
                        //webappsstore.sqlite


                        if(f?.getName().startsWith("search.json")) {
                            JsonParser jsonParser = new JsonParser();
                            jsonParser.getSearch(f.getPath())
                        }
                }
        }

    }

}


