package Model;

public class Constant {
    public enum Status {
        COMING_SOON("Coming soon"),
        END_OF_SHOWING("End of showing"),
        NOW_SHOWING("Now showing");

        private String status;
        Status(String status) {
            this.status = status;
        }

        @Override
        public String toString() { return status; }

    }

    public enum Cineplex {
        Cineleisure_Orchard("Cathay - Cineleisure Orchard"),
        Causeway_Point("Cathay - Causeway Point"),
        Downtown_East("Cathay - Downtown East"),
        JEM("Cathay - JEM"),
        ParkwayParade("Cathay - ParkwayParade"),
        TheCathay("Cathay - The Cathay"),
        WestMall("Cathay - West Mall"),
        AMK_Hub("Cathay - AMK Hub");

        private String cineplex;

        Cineplex(String cineplex) {
            this.cineplex = cineplex;
        }

        @Override
        public String toString() { return cineplex; }
    }

    public enum MovieType {
        Digital("Digital"), ThreeD("3D");

        private String movieType;
        MovieType(String movieType) {
            this.movieType = movieType;
        }

        @Override
        public String toString() {
            return movieType;
        }
    }

    public enum MovieRestriction {
        G("G"), PG("PG"), PG13("PG13"), NC16("NC16"), M18("M18"), R21("R21");

        private String movieRestriction;
        MovieRestriction(String movieRestriction) {
            this.movieRestriction = movieRestriction;
        }

        @Override
        public String toString() {
            return movieRestriction;
        }
    }
}
