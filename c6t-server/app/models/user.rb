class User < ActiveRecord::Base
  has_many :routes
  has_many :explorations
  has_many :users_explorations
  has_and_belongs_to_many :explorations_under_participation,
    join_table: :users_explorations,
    class_name: :Exploration
end
