package com.liangli.nj.bean;

import java.util.ArrayList;
import java.util.List;

import com.liangli.nj.database.BaseTable;

public class chinese_unit extends BaseTable{
        private int _id;
        private String course;
        private int bookid;
        private String unitid;
        private String unit;
        private String name;
        private String permission;
        private String kewenAuthor;
        private String lession;
        private int type;
        private String read;
        private String cizu;
        private String audio_filename;
        private int displayOrder;
        private int unit_edition;
        private String ext_chengyu;
        private String permissiongroup;
        private List<String> chinese_unit_list = new ArrayList<>();


        public chinese_unit(int _id, String course, int bookid, String unitid, String unit, String name,
				String permission, String kewenAuthor, String lession, int type, String read, String cizu,
				String audio_filename, int displayOrder, int unit_edition, String ext_chengyu, String permissiongroup) {
			super();
			this._id = _id;
			this.course = course;
			this.bookid = bookid;
			this.unitid = unitid;
			this.unit = unit;
			this.name = name;
			this.permission = permission;
			this.kewenAuthor = kewenAuthor;
			this.lession = lession;
			this.type = type;
			this.read = read;
			this.cizu = cizu;
			this.audio_filename = audio_filename;
			this.displayOrder = displayOrder;
			this.unit_edition = unit_edition;
			this.ext_chengyu = ext_chengyu;
			this.permissiongroup = permissiongroup;			
		}


		public chinese_unit() {
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


		public List<String> getChinese_unit_list() {
			chinese_unit_list.add(Integer.toString(this._id));
			chinese_unit_list.add(this.course);
			chinese_unit_list.add(Integer.toString(this.bookid));
			chinese_unit_list.add(this.unitid);
			chinese_unit_list.add(this.unit);
			
			chinese_unit_list.add(this.name);	
			chinese_unit_list.add(this.permission);
			chinese_unit_list.add(this.kewenAuthor);
			chinese_unit_list.add(this.lession);
			chinese_unit_list.add(Integer.toString(this.type));
			
			chinese_unit_list.add(this.read);
			chinese_unit_list.add(this.cizu);
			chinese_unit_list.add(this.audio_filename);
			chinese_unit_list.add(Integer.toString(this.displayOrder));
			chinese_unit_list.add(Integer.toString(this.unit_edition));

			chinese_unit_list.add(this.ext_chengyu);
			chinese_unit_list.add(this.permissiongroup);
			chinese_unit_list.add(this.ext_chengyu);

			return chinese_unit_list;
		}
		
}