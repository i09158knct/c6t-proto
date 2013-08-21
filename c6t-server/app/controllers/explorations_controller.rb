class ExplorationsController < ApplicationController
  before_action :set_exploration, only: [:show, :edit, :update, :destroy]

  # GET /explorations
  # GET /explorations.json
  def index
    @explorations = Exploration.all
  end

  # GET /explorations/1
  # GET /explorations/1.json
  def show
  end

  # GET /explorations/new
  def new
    @exploration = Exploration.new
  end

  # GET /explorations/1/edit
  def edit
  end

  # POST /explorations
  # POST /explorations.json
  def create
    @exploration = Exploration.new(exploration_params)

    respond_to do |format|
      if @exploration.save
        format.html { redirect_to @exploration, notice: 'Exploration was successfully created.' }
        format.json { render action: 'show', status: :created, location: @exploration }
      else
        format.html { render action: 'new' }
        format.json { render json: @exploration.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /explorations/1
  # PATCH/PUT /explorations/1.json
  def update
    respond_to do |format|
      if @exploration.update(exploration_params)
        format.html { redirect_to @exploration, notice: 'Exploration was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: 'edit' }
        format.json { render json: @exploration.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /explorations/1
  # DELETE /explorations/1.json
  def destroy
    @exploration.destroy
    respond_to do |format|
      format.html { redirect_to explorations_url }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_exploration
      @exploration = Exploration.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def exploration_params
      params.require(:exploration).permit(:start_time, :route_id, :user_id, :current_quest_number, :current_mission_completed_number_count, :photographed, :description)
    end
end
