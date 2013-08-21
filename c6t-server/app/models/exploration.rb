class Exploration < ActiveRecord::Base
  belongs_to :route
  belongs_to :host,
    foreign_key: :user_id,
    class_name: :User
  has_and_belongs_to_many :users,
    join_table: :users_explorations,
    class_name: :User
end
