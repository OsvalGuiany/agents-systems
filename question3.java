import com.mysql.jdbc.*;

/**
 * IMPORTANT toutes les méthodes d'éxécutions de requêtes sont des méthodes
 * succeptibles de soulever des exceptions et doivent en conséquent 
 * être entourées par un try ... catch
 */
class Question3{

    private static Connection con = null;

    /**
     * cette méthode permet d'attribuer une valeur à la variable
     * con qui est statique donc ne doit s'attribuer qu'une seule
     * fois si elle est null.
     */
    public static void setConnection(){
        if (con != null ) {
            //con = connection;
        }
    }

    /**
     * 
     * @return le resultSet contenant les numéros classés par 
     * type de spectacle et par thème.
     */
    public ResultSet getNumeros(){
        setConnection();
        String query = "SELECT N.codeNumero, N.titre, N.duree, N.theme, n.resume, AVG(E.note) AS note"
             +"FROM numeros N, Evaluations E"
             +"WHERE N.codeNUmero = E.codeNUmero"
             +"GROUP BY N.codeNumero"
             +"ORDER BY note DESC;";
        Statement st = con.createStatement();
        ResulSet rs = st.executeQuery(query);
        return rs;
    }

    /**
     * 
     * @param theme c'est le code du thème donc on veut avoir 
     * les différents numéros participants
     * @return le resultSet contenant les numéros correspondant au 
     * thème passé en paramètre
     */
    public ResultSet getNumerosParTheme(String theme){
        setConnection();

        String query = "SELECT N.codeNumero, N.titre, N.duree, N.resume, AVG(E.note) AS note"
                        +"FROM Numeros N, Evaluations E"
                        +"WHERE N.codeNumero = E.codeNUmero AND N.theme  = ?"
                        +"GROUP BY N.codeNUmero"
                        +"ORDER BY note DESC;";

        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, theme);
        
        ResultSet rs = ps.executeQuery();

        return rs;
    }

    /**
     * 
     * @return true si tous les numéros qui vont participer à un spectacle
     * correspondent bien au thème et false dans d'autres cas.
     */
    public boolean verifierSpectacleNumeros(){
        setConnection();
        boolean coherent = true;

        String query = "SELECT N.codeNumero, N.titre, N.theme"
                        +"FROM Numeros N, Spectacle S, estContenu, EC"
                        +"WHERE  N.codeNumero = EC.codeNumero AND S.codeSpectacle = EC.codeSpectacle"
                        +"AND N.theme != S.theme;";
        
        Statement st = con.createStatement();

        ResultSet rs = st.executeQuery(query);

        if(rs.hasNext()){
            throw new Exception("Le numero "+rs.getString(2)+" de numéro  "
               +rs.get(1)+"est dans un spectacle de thème différent");
        }

        return coherent;
    }

    /**
     * 
     * @return vrai si la durée de tous les spectacles est inférieure
     * ou égale à 180 minutes et false dans d'autres cas.
     */
    public boolean verifierDureeSpectacles(){
        setConnection();

        String query = "SELECT S.codeSpectacle, S.jour, S.heure, S.theme SUM(N.duree) AS duree"
                        +"FROM Spectacles S, Numeros N, EstContenu EC"
                        +"WHERE S.codeSpectacle =  EC.codeSpectacle AND N.codeNumero = EC.codeNumero"
                        +"GROUP BY S.codeSpectacle"
                        +"HAVING duree > 180;";
        
        Statement st = con.createStatement();

        ResultSet rs = st.executeQuery(query);

        if(rs.hasNext()){
            throw new Exception("Le spectacle de code "+rs.getInt(1)+
                "a une durée supérieure à 180 minutes");
        }

        return true;
    }

    /**
     * 
     * @param codeSpectacle code du spectacle auquel on veuta jouter
     * un numéro
     * @param codeNumero code du numéro qu'on veut ajouter au spectacle
     * @return true si le numéro est enregistré au spectacle et la base 
     * données est cohérente et false dans tous les autres. Si l'enregistrement
     * du numéro le spectacle ne permet pas de garder la cohérence, il sera 
     * annulé.
     */
    public boolean addNumeroInSpectacle(int codeSpectacle, int codeNumero){
        boolean ajouter = false;
        String query;
        Statement st = con.createStatement();
        PreparedStatement ps;

        st.execute("START TRANSACTION ;");

        query = "INSERT INTO EstContenu(codeNumero, codeSpectacle)"
                    +"VALUES( ?, ?);";
        
        ps = con.prepareStatement(query);

        ps.setInt(1, codeNumero);
        ps.setInt(2, codeSpectacle);
        
        // dans ce cas, la réquête ne s'éxécute pas correctement
        // on retourne donc un false.
        if( !ps.execute() ) {
            return false;
        }

        query = "SELECT S.codeSpectacle, S.jour, S.heure, S.theme SUM(N.duree) AS duree"
                +"FROM Spectacles S, Numeros N, EstContenu EC"
                +"WHERE S.codeSpectacle =  EC.codeSpectacle AND N.codeNumero = EC.codeNumero"
                +"AND S.codeSpectacle = ?";
        
        ps = con.prepareStatement(query);
        ps.setInt(1, codeSpectacle);

        ResultSet rs = ps.executeQuery();

        if(rs.hasNext()){
            if(rs.getInt("duree") > 180){
                st.execute("ROLLBACK ;");
                throw new Exception("la durée du spectacle auquelle vous voulez ajouter"
                        +"un numéro ne doit pas dépasser 180 minutes");
            } else {
                st.execute("COMMIT ;");
                return true;
            }
        } else {
            st.execute("COMMIT");

            return true;
        }

        return ajouter;
    }

}