class CreateQuests < ActiveRecord::Migration
  def change
    create_table :quests do |t|
      t.string :location, null: false
      t.text :pose, null: false
      t.text :mission, null: false
      t.string :photo
      t.references :route, index: true, null: false

      t.timestamps
    end
  end
end
