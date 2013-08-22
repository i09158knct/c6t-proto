json.extract! exploration,
  :start_time,
  :current_quest_number,
  :current_mission_completed_number_count,
  :photographed,
  :description,
  :created_at,
  :updated_at

json.route do |json|
  json.partial! exploration.route
end

json.user do |json|
  json.partial! exploration.host
end
