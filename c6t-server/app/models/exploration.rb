class Exploration < ActiveRecord::Base
  belongs_to :route
  belongs_to :host,
    foreign_key: :user_id,
    class_name: :User
  has_and_belongs_to_many :members,
    join_table: :users_explorations,
    class_name: :User,
    uniq: true

  def start!
    unless started?
      self.current_quest_number = 0
      self.route.played_count += 1
      self.route.save
    end
  end

  def put_group_photo!(quest_number)
    if self.current_quest_number == quest_number
      self.photographed = true
    end

    change_quest_state!
  end

  def put_mission_photo!(quest_number)
    if self.current_quest_number == quest_number
      self.current_mission_completed_number_count += 1
    end

    change_quest_state!
  end

  private
    STATE_NOT_STARTED = -1
    STATE_COMPLETED = -2

    def started?
      self.current_quest_number != STATE_NOT_STARTED
    end

    def completed?
      self.current_quest_number == STATE_COMPLETED
    end

    def change_quest_state!
      if self.photographed
        if self.current_quest_number == self.route.quests.length - 1
          self.current_quest_number = STATE_COMPLETED
          self.photographed = false
          self.route.achievement_count += 1
          self.route.save


        elsif self.current_mission_completed_number_count >= self.members.length
          self.current_quest_number += 1
          self.current_mission_completed_number_count = 0
          self.photographed = false
        end
      end
    end
end
