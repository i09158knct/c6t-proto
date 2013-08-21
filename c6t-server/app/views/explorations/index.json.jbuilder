json.array!(@explorations) do |exploration|
  json.extract! exploration, :start_time, :route_id, :user_id, :current_quest_number, :current_mission_completed_number_count, :photographed, :description
  json.url exploration_url(exploration, format: :json)
end
