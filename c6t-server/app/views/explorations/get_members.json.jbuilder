json.array!(@members) do |member|
  json.partial! member
  json.url user_url(member, format: :json)
end
