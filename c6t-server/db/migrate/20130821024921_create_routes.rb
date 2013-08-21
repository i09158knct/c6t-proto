class CreateRoutes < ActiveRecord::Migration
  def change
    create_table :routes do |t|
      t.string :name, null: false
      t.integer :achievement_count, default: 0
      t.integer :played_count, default: 0
      t.string :start_location, null: false
      t.text :description, null: false
      t.references :user, index: true, null: false

      t.timestamps
    end
    add_index :routes, :name, unique: true
  end
end
