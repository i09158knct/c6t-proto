class CreateUsersExplorations < ActiveRecord::Migration
  def change
    create_table :users_explorations, id: false do |t|
      t.integer :user_id
      t.integer :exploration_id
    end
  end
end
