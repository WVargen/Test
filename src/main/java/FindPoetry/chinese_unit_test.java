package FindPoetry;

import testJDBC.MyAnnotation;

public class chinese_unit_test {
		@MyAnnotation(name = "_id",order = 1)
        private int _id;
		@MyAnnotation(name = "course",order = 2)
        private String course;
		@MyAnnotation(name = "bookid",order = 3)
        private int bookid;
		@MyAnnotation(name = "unitid",order = 4,editable = false)
        private String unitid;
		@MyAnnotation(name = "单元",order = 5)
        private String unit;
		@MyAnnotation(name = "课文名",order = 6)
        private String name;
		@MyAnnotation(name = "课文作者",order = 7,editable = false)
        private String kewenAuthor;
		@MyAnnotation(name = "序号",order = 8)
        private String lession;
		@MyAnnotation(name = "type",order = 9)
        private int type;
		@MyAnnotation(name = "内容",order = 10)
        private String read;
		@MyAnnotation(name = "词组",order = 11)
        private String cizu;
		@MyAnnotation(name = "录音文件名",order = 12,editable = false)
        private String audio_filename;
		@MyAnnotation(name = "展示顺序",order = 13,editable = false)
        private int displayOrder;
		@MyAnnotation(name = "单元编辑",order = 14,editable = false)
        private int unit_edition;
		@MyAnnotation(name = "成语",order = 15)
        private String ext_chengyu;
		@MyAnnotation(name = "permission",order = 16,editable = false)
        private String permission;
		@MyAnnotation(name = "permissiongroup",order = 17,editable = false)
        private String permissiongroup;

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
}