package jp.knct.di.c6t.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
	private int id;
	private String name;
	private String area;

	public User(int id, String name, String area) {
		this.setId(id);
		this.setName(name);
		this.setArea(area);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(getId());
		dest.writeString(getName());
		dest.writeString(getArea());
	}

	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		@Override
		public User createFromParcel(Parcel source) {
			int id = source.readInt();
			String area = source.readString();
			String name = source.readString();
			return new User(id, name, area);
		}

		@Override
		public User[] newArray(int size) {
			// TODO Auto-generated method stub
			return new User[size];
		}
	};
}
