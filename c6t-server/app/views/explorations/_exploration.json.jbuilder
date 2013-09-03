json.extract! exploration,
  :id,
  :start_time,
  :current_quest_number,
  :current_mission_completed_number_count,
  :photographed,
  :description,
  :created_at,
  :updated_at,
  :host

json.route do |json|
  json.partial! exploration.route
end

json.members do |json|
  json.array(exploration.members) do |member|
    json.partial! member
  end
end