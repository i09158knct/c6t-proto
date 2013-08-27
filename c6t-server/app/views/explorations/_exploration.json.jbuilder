json.extract! exploration,
  :id,
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

# NOTICE: For consistency with Android C6t app program,
# property name of creator of exploration should be "user",
# should NOT be "host".
json.user do |json|
  json.partial! exploration.host
end

json.members do |json|
  json.array(exploration.members) do |member|
    json.partial! member
  end
end