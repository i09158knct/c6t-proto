class CreateExplorations < ActiveRecord::Migration
  def change
    create_table :explorations do |t|
      t.datetime :start_time, null: false
      t.references :route, index: true, null: false
      t.references :user, index: true, null: false
      t.integer :current_quest_number, default: -1
      t.integer :current_mission_completed_number_count, default: 0
      t.boolean :photographed, default: false
      t.text :description, null: false

      t.timestamps
    end
  end
end
