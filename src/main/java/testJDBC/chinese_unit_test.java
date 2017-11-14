package testJDBC;

class chinese_unit_test {
        private int _id;
        private String course;
        private int bookid;
        private String unitid;
        private String unit;
        private String name;
        private String kewenAuthor;
        private String lession;
        private int type;
        private String read;
        private String cizu;
        private String audio_filename;
        private int displayOrder;
        private int unit_edition;
        private String ext_chengyu;
        private String permission;
        private String permissiongroup;
//        private static String[] title = {"_id","course","bookid","unitid","unit","name","permission",
//        		"kewenAuthor","lession","type","read","cizu","audio_filename","displayOrder",
//        		"unit_edition","ext_chengyu","permissiongroup"};
        private static String[] title = {"_id","course","bookid","单元","序号","课文名","type","识字表","写字表","词组","成语"};
        
        public static void setTitle(String[] title) {
			chinese_unit_test.title = title;
		}

		public static String[] getTitle() {
			return title;
		}

		public chinese_unit_test(int _id, String course, int bookid, String unitid, String unit, String name,
				 String kewenAuthor, String lession, int type, String read, String cizu,
				String audio_filename, int displayOrder, int unit_edition, String ext_chengyu,String permission, String permissiongroup) {
			super();
			this._id = _id;
			this.course = course;
			this.bookid = bookid;
			this.unitid = unitid;
			this.unit = unit;
			this.name = name;
			this.kewenAuthor = kewenAuthor;
			this.lession = lession;
			this.type = type;
			this.read = read;
			this.cizu = cizu;
			this.audio_filename = audio_filename;
			this.displayOrder = displayOrder;
			this.unit_edition = unit_edition;
			this.ext_chengyu = ext_chengyu;
			this.permission = permission;
			this.permissiongroup = permissiongroup;			
		}


		public chinese_unit_test() {
			// TODO Auto-generated constructor stub
		}

		public int get_id() {
			return _id;
		}


		public void set_id(int _id) {
			this._id = _id;
		}


		public String getCourse() {
			return course;
		}


		public void setCourse(String course) {
			this.course = course;
		}


		public int getBookid() {
			return bookid;
		}


		public void setBookid(int bookid) {
			this.bookid = bookid;
		}


		public String getUnitid() {
			return unitid;
		}


		public void setUnitid(String unitid) {
			this.unitid = unitid;
		}


		public String getUnit() {
			return unit;
		}


		public void setUnit(String unit) {
			this.unit = unit;
		}


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		public String getPermission() {
			return permission;
		}


		public void setPermission(String permission) {
			this.permission = permission;
		}


		public String getKewenAuthor() {
			return kewenAuthor;
		}


		public void setKewenAuthor(String kewenAuthor) {
			this.kewenAuthor = kewenAuthor;
		}


		public String getLession() {
			return lession;
		}


		public void setLession(String lession) {
			this.lession = lession;
		}


		public int getType() {
			return type;
		}


		public void setType(int type) {
			this.type = type;
		}


		public String getRead() {
			return read;
		}


		public void setRead(String read) {
			this.read = read;
		}


		public String getCizu() {
			return cizu;
		}


		public void setCizu(String cizu) {
			this.cizu = cizu;
		}


		public String getAudio_filename() {
			return audio_filename;
		}


		public void setAudio_filename(String audio_filename) {
			this.audio_filename = audio_filename;
		}


		public int getDisplayOrder() {
			return displayOrder;
		}


		public void setDisplayOrder(int displayOrder) {
			this.displayOrder = displayOrder;
		}


		public int getUnit_edition() {
			return unit_edition;
		}


		public void setUnit_edition(int unit_edition) {
			this.unit_edition = unit_edition;
		}


		public String getExt_chengyu() {
			return ext_chengyu;
		}


		public void setExt_chengyu(String ext_chengyu) {
			this.ext_chengyu = ext_chengyu;
		}


		public String getPermissiongroup() {
			return permissiongroup;
		}


		public void setPermissiongroup(String permissiongroup) {
			this.permissiongroup = permissiongroup;
		}


		public String[] getChinese_unit_string() {
			String[] chinese_unit_list = {Integer.toString(this._id),this.course,Integer.toString(this.bookid),
					this.unit,this.lession,this.name,Integer.toString(this.type),this.read,this.read,this.cizu,this.ext_chengyu};
			return chinese_unit_list;
		}
		
}