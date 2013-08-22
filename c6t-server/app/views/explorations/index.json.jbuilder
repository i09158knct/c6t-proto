json.array!(@explorations) do |exploration|
  json.partial! exploration
  json.url exploration_url(exploration, format: :json)
end
