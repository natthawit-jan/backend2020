from flask import Flask, jsonify, request

app = Flask(__name__)

tallies = dict()

@app.route("/status")
def get_status():
    return jsonify(tallies)

@app.route("/vote", methods=['POST'])
def post_vote():
    body = request.json
    voter_id, vote_for_id = body.get('your_id'), body.get('vote_for')
    if (voter_id and vote_for_id):
        tallies[vote_for_id] = tallies.get(vote_for_id, 0) + 1
        return jsonify({'status' : 'ok'})
    else: 
        res = jsonify({'status': 'ERROR'})
        res.status_code = 400
        return response

