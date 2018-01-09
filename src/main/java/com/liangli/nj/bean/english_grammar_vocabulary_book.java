package com.liangli.nj.bean;

import com.liangli.nj.database.BaseTable;

public class english_grammar_vocabulary_book extends BaseTable{
	int _id;
	String course;
	int bookid;
	String school_version;
	int displayOrder;
	String name;
	String grade;
	String period;
	int periodOrder;
	String press;
	String cover_filename;
	String permission;
	String permissiongroup;
	int isNew;
	int isHot;
	int course_edition;
	int isFree;
	
	
	public english_grammar_vocabulary_book() {
		super();
	}

	public english_grammar_vocabulary_book(int _id, String course, int bookid, String school_version, int displayOrder,
			String name, String grade, String period, int periodOrder, String press, String cover_filename,
			String permission, String permissiongroup, int isNew, int isHot, int course_edition, int isFree) {
		super();
		this._id = _id;
		this.course = course;
		this.bookid = bookid;
		this.school_version = school_version;
		this.displayOrder = displayOrder;
		this.name = name;
		this.grade = grade;
		this.period = period;
		this.periodOrder = periodOrder;
		this.press = press;
		this.cover_filename = cover_filename;
		this.permission = permission;
		this.permissiongroup = permissiongroup;
		this.isNew = isNew;
		this.isHot = isHot;
		this.course_edition = course_edition;
		this.isFree = isFree;
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

	public String getSchool_version() {
		return school_version;
	}

	public void setSchool_version(String school_version) {
		this.school_version = school_version;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public int getPeriodOrder() {
		return periodOrder;
	}

	public void setPeriodOrder(int periodOrder) {
		this.periodOrder = periodOrder;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}

	public String getCover_filename() {
		return cover_filename;
	}

	public void setCover_filename(String cover_filename) {
		this.cover_filename = cover_filename;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getPermissiongroup() {
		return permissiongroup;
	}

	public void setPermissiongroup(String permissiongroup) {
		this.permissiongroup = permissiongroup;
	}

	public int getIsNew() {
		return isNew;
	}

	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}

	public int getIsHot() {
		return isHot;
	}

	public void setIsHot(int isHot) {
		this.isHot = isHot;
	}

	public int getCourse_edition() {
		return course_edition;
	}

	public void setCourse_edition(int course_edition) {
		this.course_edition = course_edition;
	}

	public int getIsFree() {
		return isFree;
	}

	public void setIsFree(int isFree) {
		this.isFree = isFree;
	}
	
	
}
