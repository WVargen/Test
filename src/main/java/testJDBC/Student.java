package testJDBC;

class Student {
        private String Id;
        private String Name;
        private String Sexual;
        private String Age;

        Student(String Name, String Sexual, String Age) {
            this.Id = null; //default
            this.Name = Name;
            this.Sexual = Sexual;
            this.Age = Age;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getSexual() {
            return Sexual;
        }

        public void setSexual(String Sexual) {
            this.Sexual = Sexual;
        }

        public String getAge() {
            return Age;
        }

        public void setage(String Age) {
            this.Age = Age;
        }
}